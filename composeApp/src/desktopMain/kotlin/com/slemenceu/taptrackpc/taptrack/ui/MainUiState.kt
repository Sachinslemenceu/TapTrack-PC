package com.slemenceu.taptrackpc.taptrack.ui

data class MainUiState(
    val isConnected: Boolean = false,
    val isLoading: Boolean = false,
    val ipAddress : String = "",
    val passcode: String = "",
    val serverIp: String = "",
    val serverPort: Int = 0,
    val deviceName: String = "",
    val networkName: String = ""
)
