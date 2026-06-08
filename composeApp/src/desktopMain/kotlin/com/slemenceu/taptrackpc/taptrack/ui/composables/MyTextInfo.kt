package com.slemenceu.taptrackpc.taptrack.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyTextInfo(
    title: String,
    info: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF7D8694),
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Surface(
            color = Color(0xFF111318),
            border = BorderStroke(1.dp, Color(0xFF1E2330)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = info,
                fontSize = 14.sp,
                color = Color(0xFF00E5A0),
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}