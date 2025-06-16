package com.slemenceu.taptrackpc.taptrack.ui

data class MainUiState(
    val isConnected: Boolean = false,
    val isLoading: Boolean = false,
    val ipAddress : String = "",
    val passcode: String = ""
)
