package com.yeongdon.bluetoothstudy.domain.chat

// 제네릭 타입으로 사용하기 위해서 typealias 사용 , Top level 변수로 정의
typealias BluetoothDeviceDomain = BluetoothDevice

// BluetoothDevice 정보 data
data class BluetoothDevice(
    val name: String?,
    // MAC address
    val address: String
)
