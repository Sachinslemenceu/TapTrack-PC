package com.slemenceu.taptrackpc.taptrack.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrackpc.taptrack.ui.MainUiState
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color


@Composable
fun ConnectedScreen(
    uiState: MainUiState
){
    Image(
        painter = painterResource("drawable/network.png"),
        contentDescription = "Connected logo",
        modifier = Modifier
            .size(150.dp)
    )
    Text(
        "Connected",
        color = Color.Green,
        modifier = Modifier.padding(10.dp),
    )
    Text(
        text = "The Connected Ip is : ${uiState.ipAddress}",
        color = Color.Blue
    )

}