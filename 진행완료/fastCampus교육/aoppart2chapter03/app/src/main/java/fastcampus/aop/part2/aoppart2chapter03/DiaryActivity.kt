package fastcampus.aop.part2.aoppart2chapter03

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitiy_diary)

        val diaryEditText: EditText by lazy {
            findViewById<EditText>(R.id.diaryEditText)
        }
        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))



        val runnable = Runnable {
            getSharedPreferences("diary",Context.MODE_PRIVATE).edit{
                putString("detail", diaryEditText.text.toString())
            }

            Log.d("DiaryActivity","SAVE!! ${diaryEditText.text.toString()}")
        }

        diaryEditText.addTextChangedListener {

            Log.d("DidaryActivity","TextChanged :: $it")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable,500)

        }
    }
}