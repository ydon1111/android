package com.yeongdon.bluetoothstudy.data.chat

import com.yeongdon.bluetoothstudy.domain.chat.BluetoothMessage

// string to bluetooth message
fun String.toBluetoothMessage(isFromLocalUser: Boolean): BluetoothMessage{
    val name = substringBeforeLast("#")
    val message = substringAfter("#")
    return BluetoothMessage(
        message = message,
        senderName = name,
        isFromLocalUser = isFromLocalUser
    )
}

// message to byte
fun BluetoothMessage.toByteArray(): ByteArray{
    return "$senderName#$message".encodeToByteArray()
}