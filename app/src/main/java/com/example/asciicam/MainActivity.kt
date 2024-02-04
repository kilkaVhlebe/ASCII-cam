package com.example.asciicam

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.awaitAll
import java.util.Timer


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectButton: Button = findViewById(R.id.selectButton)

        selectButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            @Suppress("DEPRECATION")
            startActivityForResult(intent, 1)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            try {
                val textView = findViewById<TextView>(R.id.textView)
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)

                val fileUri: Uri? = data?.data
                val inputStream = contentResolver.openInputStream(fileUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                val displayMetrics = DisplayMetrics()
                val stringBuilder = StringBuilder()
                val imageConverter = ImageConverter()

                if (bitmap != null) {
                    @Suppress("DEPRECATION")
                    windowManager.defaultDisplay.getMetrics(displayMetrics)
                    val height = displayMetrics.heightPixels
                    val width = displayMetrics.widthPixels

                    val resizeBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, width/20, height/17, false)
                    val grayscaleBitmap: Bitmap = imageConverter.setGrayscaleBitmap(resizeBitmap)
                    val asciiArt: Array<Array<String>> = imageConverter.convertToASCII(grayscaleBitmap)

                    progressBar.visibility = View.VISIBLE

                    for (row in asciiArt) {
                        for (value in row) {
                            stringBuilder.append("$value\t")
                        }
                        stringBuilder.append("\n")
                    }

                    progressBar.visibility = View.INVISIBLE

                    textView.text = stringBuilder.toString()
                }

            } catch (e: Exception) {
                Log.e("myTag", "Error reading file: ${e.message}")
            }
        }
    }