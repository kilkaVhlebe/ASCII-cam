package com.example.asciicam

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint

class ImageConverter {
    private val matrix = ColorMatrix()
    private val filter: ColorMatrixColorFilter

    init {
        matrix.setSaturation(0f)
        filter = ColorMatrixColorFilter(matrix)
    }

    fun setGrayscaleBitmap(bitmap: Bitmap): Bitmap {
        val grayscaleBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val paint = Paint()
        paint.setColorFilter(filter)
        val canvas = Canvas(grayscaleBitmap)
        canvas.drawBitmap(grayscaleBitmap, 0f, 0f, paint)
        return grayscaleBitmap
    }

    fun convertToASCII(bitmap: Bitmap): Array<Array<String>> {
        val width = bitmap.width
        val height = bitmap.height
        val asciiArt = Array(height) { Array(width) { "" } }
        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = bitmap.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)
                val brightness = (blue + green + red) / 3.0
                when (brightness) {
                    in 0.0..23.18 -> asciiArt[y][x] = matrixInput(0)
                    in 23.19..46.3618 -> asciiArt[y][x] = matrixInput(1)
                    in 46.3619..69.5418 -> asciiArt[y][x] = matrixInput(2)
                    in 69.5418..92.7218 -> asciiArt[y][x] = matrixInput(3)
                    in 92.7219..115.9018 -> asciiArt[y][x] = matrixInput(4)
                    in 115.9019..139.0818 -> asciiArt[y][x] = matrixInput(5)
                    in 139.0819..162.2618 -> asciiArt[y][x] = matrixInput(6)
                    in 162.2619..185.4418 -> asciiArt[y][x] = matrixInput(7)
                    in 185.4419..208.6218 -> asciiArt[y][x] = matrixInput(8)
                    in 208.6219..231.8018 -> asciiArt[y][x] = matrixInput(9)
                    in 231.8019..255.0 -> asciiArt[y][x] = matrixInput(10)
                }
            }
        }
        return asciiArt
    }

    fun matrixInput(index: Int): String {
        val SymbolsArr = arrayOf("@", "#", "S", "%", "?", "*", "+", ";", ":", ",", ".")
        return SymbolsArr[index]
    }
}

