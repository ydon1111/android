package com.yeongdon.bluetoothstudy.data.chat

import android.bluetooth.BluetoothSocket
import com.yeongdon.bluetoothstudy.domain.chat.BluetoothMessage
import com.yeongdon.bluetoothstudy.domain.chat.TransferFailedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException

// message 관리
class BluetoothDataTransferService(
    private val socket: BluetoothSocket
) {
    // Bluetooth connection result를 알고 있기 때문에 BluetoothMessage를 받아옴
    fun listenForIncomingMessages(): Flow<BluetoothMessage> {
        return flow {
            if (!socket.isConnected) {
                return@flow
            }
            val buffer = ByteArray(1024)
            while (true) {
                val byteCount = try {
                    socket.inputStream.read(buffer)
                } catch (e: IOException) {
                    throw TransferFailedException()
                }

                emit(
                    buffer.decodeToString(
                        endIndex = byteCount
                    ).toBluetoothMessage(
                        isFromLocalUser = false
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun sendMessage(bytes: ByteArray): Boolean{
        return withContext(Dispatchers.IO){
            try {
                socket.outputStream.write(bytes)
            } catch (e: IOException){
                e.printStackTrace()
                return@withContext false
            }
            true
        }
    }

}