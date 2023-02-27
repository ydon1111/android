package com.preonboarding.videorecorder.presentation.record

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.AudioAttributes
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.preonboarding.videorecorder.R
import com.preonboarding.videorecorder.const.REQUEST_CODE_PERMISSIONS
import com.preonboarding.videorecorder.const.REQUIRED_PERMISSIONS
import com.preonboarding.videorecorder.const.TAG
import com.preonboarding.videorecorder.databinding.FragmentRecordBinding
import com.preonboarding.videorecorder.domain.model.Video
import com.preonboarding.videorecorder.presentation.MainViewModel
import com.preonboarding.videorecorder.presentation.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {
    private val recordVideoModel: MainViewModel by activityViewModels()
    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null
    private lateinit var cameraExecutor: ExecutorService
    private var cameraFacing = CameraSelector.DEFAULT_BACK_CAMERA
    var pauseTime = 0L
    private var soundpool: SoundPool? = null
    private var sound: Int = 0
    private lateinit var name: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initSound()

        if (checkPermissions()) {
            initCamera(cameraFacing)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun initSound() {
        soundpool = SoundPool.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .setMaxStreams(10)
            .build()
        sound = soundpool?.load(requireContext(), R.raw.alert, 1)!!
    }

    private fun releaseSound() {
        val sp = soundpool ?: return
        CoroutineScope(Dispatchers.Default).launch {
            sp.release()
        }
        soundpool = null
    }

    private fun initView() {
        binding.apply {

            btnRecord.setOnClickListener {
                startRecord()
            }

            btnStop.setOnClickListener {
                val curRecording = recording
                if (curRecording != null) {
                    curRecording.stop()
                    recording = null
                }
                binding.chronometer.stop()
                pauseTime = binding.chronometer.base
            }

            binding.btnSwitch.setOnClickListener {
                if (cameraFacing == CameraSelector.DEFAULT_FRONT_CAMERA) {
                    cameraFacing = CameraSelector.DEFAULT_BACK_CAMERA
                    initCamera(cameraFacing)
                } else {
                    cameraFacing = CameraSelector.DEFAULT_FRONT_CAMERA
                    initCamera(cameraFacing)
                }
                try {

                } catch (_: Exception) {
                }
            }

        }

    }


    private fun checkPermissions() = REQUIRED_PERMISSIONS.all { it: String ->
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (checkPermissions()) {
                initCamera(cameraFacing)
            } else {
                Snackbar.make(
                    requireView(),
                    "Permissions not granted by the user.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    //@RequiresPermission(Manifest.permission.CAMERA)
    private fun initCamera(cameraFacing: CameraSelector) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraView.surfaceProvider)
                }
            val cameraSelector = cameraFacing

            try {
                cameraProvider.unbindAll()

                val myCamera = cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    videoCapture
                )
                binding.seekbar.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        myCamera.cameraControl.setLinearZoom(progress / 100.toFloat())
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

                })


            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))

        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
            .build()
        videoCapture = VideoCapture.withOutput(recorder)
    }


    private fun startRecord() {
        val videoCapture = this.videoCapture ?: return
        binding.btnRecord.isEnabled = false
        val curRecording = recording
        if (curRecording != null) {
            curRecording.stop()
            recording = null
            return
        }
        name =
            "Wanted_camera" + SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/Wanted-Video")
            }
        }

        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(requireActivity().contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()

        recording = videoCapture.output
            .prepareRecording(requireContext(), mediaStoreOutputOptions)
            .apply {
                if (PermissionChecker.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.RECORD_AUDIO
                    ) ==
                    PermissionChecker.PERMISSION_GRANTED
                ) {
                    withAudioEnabled()
                }
            }
            .start(ContextCompat.getMainExecutor(requireContext())) { recordEvent ->
                when (recordEvent) {
                    is VideoRecordEvent.Start -> {
                        //영상 녹화 시작시
                        binding.apply {
                            btnRecord.isEnabled = false
                            btnRecord.visibility = View.GONE
                            btnPause.isEnabled = true
                            btnPause.visibility = View.VISIBLE
                            btnStop.isEnabled = true
                            btnStop.visibility = View.VISIBLE

                            btnSwitch.visibility = View.GONE
                            btnSwitch.isEnabled = false


                            btnPause.setOnClickListener {
                                val curRecording = recording
                                if (curRecording != null) {
                                    curRecording?.pause()
                                }
                            }
                            chronometer.base = SystemClock.elapsedRealtime()
                            chronometer.start()
                        }
                        soundpool?.play(sound, 1f, 1f, 0, 0, 1f)
                    }
                    is VideoRecordEvent.Pause -> {
                        binding.apply {
                            pauseTime = chronometer.base
                            chronometer.stop()
                            btnPause.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24)
                            btnPause.setOnClickListener {
                                recording?.resume()
                            }

                        }
                    }

                    is VideoRecordEvent.Resume -> {
                        binding.apply {
                            btnPause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                            btnPause.setOnClickListener {
                                recording?.pause()
                            }
                            chronometer.base = pauseTime
                            chronometer.start()
                        }
                    }

                    is VideoRecordEvent.Finalize -> {
                        if (!recordEvent.hasError()) {
                            //영상 녹화 종료시
                            binding.apply {
                                btnRecord.isEnabled = true
                                btnRecord.visibility = View.VISIBLE
                                btnPause.isEnabled = false
                                btnPause.visibility = View.GONE
                                btnStop.isEnabled = false
                                btnStop.visibility = View.GONE

                                btnSwitch.visibility = View.VISIBLE
                                btnSwitch.isEnabled = true

                                chronometer.stop()
                                chronometer.base = SystemClock.elapsedRealtime()
                            }
                            val msg = "영상이 정상적으로 저장되었습니다."
                            Snackbar.make(
                                requireView(),
                                msg,
                                Snackbar.LENGTH_SHORT
                            ).show()
                            val nowDate =
                                SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis())
                                    .toString()

                            val recordedVideo = Video(
                                date = nowDate,
                                videoUrl = getPath(recordEvent.outputResults.outputUri)
                            )
                            soundpool?.play(sound, 1f, 1f, 0, 0, 1f)
                            pauseTime = 0L
                            saveFireBase(recordedVideo)
                        } else {
                            recording?.close()
                            recording = null
                        }
                    }
                }
            }
    }

    @SuppressLint("Range")
    private fun getPath(uri: Uri): String {
        val cursor: Cursor? = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.moveToNext()
        val path: String? = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()
        return path ?: ""
    }

    private fun saveFireBase(recordedVideo: Video) {
        Timber.e("$recordedVideo")
        recordVideoModel.uploadVideo(recordedVideo)
    }

    override fun onDestroyView() {
        cameraExecutor.shutdown()
        binding.chronometer.stop()
        releaseSound()

        super.onDestroyView()
    }
}
