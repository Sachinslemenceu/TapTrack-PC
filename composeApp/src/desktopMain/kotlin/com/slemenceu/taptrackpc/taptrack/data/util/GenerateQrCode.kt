package com.slemenceu.taptrackpc.taptrack.data.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.awt.image.BufferedImage

object QrCodeGenerator{
    fun generateQrCodeImage(content: String, size: Int): ImageBitmap{
        val bitMatrix: BitMatrix = MultiFormatWriter()
            .encode(content, BarcodeFormat.QR_CODE,size,size)
        val image = BufferedImage(size,size, BufferedImage.TYPE_INT_RGB)

        for(x in 0 until size){
            for (y in 0 until size){
                val color = if (bitMatrix[x,y]) 0x000000 else 0xFFFFFF
                image.setRGB(x,y,color)

            }
        }
        return image.toComposeImageBitmap()
    }
}