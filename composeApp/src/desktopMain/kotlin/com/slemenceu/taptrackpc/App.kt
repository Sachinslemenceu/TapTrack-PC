package com.slemenceu.taptrackpc

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.slemenceu.taptrackpc.taptrack.ui.MainScreen
import com.slemenceu.taptrackpc.taptrack.ui.MainViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.context.KoinContext


@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            val viewModel = koinViewModel<MainViewModel>()
            Scaffold(
                containerColor =  Color(0xFF0A0C10)
            ) {
                MainScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

