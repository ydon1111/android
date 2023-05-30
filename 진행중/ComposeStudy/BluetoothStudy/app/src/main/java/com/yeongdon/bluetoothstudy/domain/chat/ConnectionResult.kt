package com.yeongdon.bluetoothstudy.domain.chat

sealed interface ConnectionResult{
    object ConnectionEstablished: ConnectionResult
    data class Error(val message: String): ConnectionResult
}