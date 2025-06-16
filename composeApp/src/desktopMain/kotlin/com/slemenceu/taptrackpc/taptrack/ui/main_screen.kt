package com.slemenceu.taptrackpc.taptrack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrackpc.taptrack.data.util.QrCodeGenerator
import com.slemenceu.taptrackpc.taptrack.ui.composables.ConnectedScreen
import com.slemenceu.taptrackpc.taptrack.ui.composables.StartServerScreen

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    onEvent: (MainUiEvent) -> Unit
){

    val text = if (uiState.isConnected) "Connected to the TapTrack app minimize the window and enjoy."
    else "Scan the above Qr code via app to connect the Pc to the TapTrack App"

    LaunchedEffect(Unit){
        println("Server starting")
        onEvent(MainUiEvent.StartServer)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Spacer(Modifier.weight(0.5f))

        Image(
            painter = painterResource("drawable/icon_img.png"),
            contentDescription = "Image",
            modifier = Modifier
                .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
                .padding(horizontal = 10.dp)
        )
        Text(
            "TapTrack",
            color = Color(0xFF3587DF),
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.padding(10.dp)
        )

        Spacer(Modifier.weight(1f))
        if (!uiState.isConnected){
            StartServerScreen(
                uiState = uiState
            )
        } else{
            ConnectedScreen(
                uiState = uiState
            )
        }
        Spacer(Modifier.weight(1f))

        Text(
            text = text,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.padding(10.dp)
        )
        Text(
            "Developed Byㅤ♡    Slemenceu",
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.padding(10.dp)
        )


    }
}