package com.slemenceu.taptrackpc.taptrack.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrackpc.taptrack.data.util.QrCodeGenerator
import com.slemenceu.taptrackpc.taptrack.ui.MainUiState

@Composable
fun StartServerScreen(
    uiState: MainUiState,
) {
    if (!uiState.isLoading && uiState.passcode != "") {
        // Format QR code content as IP:PORT:PASSCODE
        val qrContent =
            "${uiState.serverIp}:${uiState.serverPort}:${uiState.passcode}:${uiState.deviceName}:${uiState.networkName}"
        val qrImage = remember { QrCodeGenerator.generateQrCodeImage(qrContent, 100) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp)
            ) {
                Text(
                    text = " Connect with QR ",
                    color = Color(0xFF9CA3AF),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Fastest Method",
                    color = Color(0xFF7D8694),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(18.dp))
                Image(
                    bitmap = qrImage,
                    contentDescription = qrContent,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                VerticalDivider(
                    color = Color(0xFF162338),
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            Color(0xFF111318),
                            shape = CircleShape
                        )
                        .border(
                            border = BorderStroke(1.dp, Color(0xFF1E2330)),
                            CircleShape
                        )
                        .size(50.dp),
                ) {
                    Text(
                        text = "or",
                        color = Color(0xFF7D8694),
                        fontSize = 14.sp,
                        modifier = Modifier
//                            .padding(16.dp)
                    )
                }
            }

            BackgroundThemeCard(
                modifier = Modifier
                    .weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Text(
                        text = " Connect Manually",
                        color = Color(0xFF9CA3AF),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        " If QR scanning is unavailable ",
                        color = Color(0xFF7D8694),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(16.dp))
                    MyTextInfo(
                        "IP Address",
                        uiState.serverIp,
                    )
                    Spacer(Modifier.height(16.dp))
                    MyTextInfo(
                        "Port",
                        uiState.serverPort.toString(),
                    )
                }
            }

        }

    } else {
        CircularProgressIndicator(Modifier.size(40.dp))
    }

}