package com.yeongdon.bluetoothstudy.domain.chat

import kotlinx.coroutines.flow.StateFlow


interface BluetoothController{
    val scannedDevices : StateFlow<List<BluetoothDevice>>
    val pairDevices : StateFlow<List<BluetoothDevice>>

    fun startDiscovery()
    fun stopDiscovery()

    fun release()


}
