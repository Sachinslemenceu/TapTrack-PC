package com.slemenceu.taptrackpc.taptrack.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrackpc.taptrack.data.util.QrCodeGenerator
import com.slemenceu.taptrackpc.taptrack.ui.MainUiState

@Composable
fun StartServerScreen(
    uiState: MainUiState,
){
    if (!uiState.isLoading && uiState.passcode != ""){
        val qrImage = remember { QrCodeGenerator.generateQrCodeImage(uiState.passcode, 100) }
        Image(
            bitmap = qrImage,
            contentDescription = uiState.passcode,
            modifier = Modifier
                .size(150.dp)
        )} else{
        CircularProgressIndicator(Modifier.size(40.dp))
    }

}