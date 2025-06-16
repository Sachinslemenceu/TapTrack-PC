package com.slemenceu.taptrackpc

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import initKoin

fun main() = application {
    initKoin()
    val windowState = rememberWindowState(width = 850.dp, height = 650.dp)

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "TapTrack",
    ) {
        App()
    }
}