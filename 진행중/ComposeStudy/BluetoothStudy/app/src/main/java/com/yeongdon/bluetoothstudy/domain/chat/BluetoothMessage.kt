package com.yeongdon.bluetoothstudy.domain.chat

// message data 설정
data class BluetoothMessage(
    val message: String,
    val senderName: String,
    val isFromLocalUser: Boolean
)
