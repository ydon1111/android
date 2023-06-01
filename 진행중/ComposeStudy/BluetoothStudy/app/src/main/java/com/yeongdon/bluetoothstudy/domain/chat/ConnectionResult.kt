package com.yeongdon.bluetoothstudy.domain.chat

sealed interface ConnectionResult{

    object ConnectionEstablished: ConnectionResult

    // message 전송 성공 확인
    data class TransferSucceeded(val message: BluetoothMessage): ConnectionResult

    // bluetooth 연결 실패 확인
    data class Error(val message: String): ConnectionResult
}