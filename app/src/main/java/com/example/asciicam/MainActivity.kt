package com.example.asciicam

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectButton: Button = findViewById(R.id.selectButton)

        selectButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            val fileUri: Uri? = data?.data
            try {
                val textView = findViewById<TextView>(R.id.textView)
                val stringBuilder = StringBuilder()
                val inputStream = contentResolver.openInputStream(fileUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val imageConverter = ImageConverter()

                if (bitmap != null) {
                    val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)
                    val height = displayMetrics.heightPixels
                    val width = displayMetrics.widthPixels
                    val resizeBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, width/20, height/17, false)
                    val grayscaleBitmap: Bitmap = imageConverter.setGrayscaleBitmap(resizeBitmap)
                    val asciiArt: Array<Array<String>> = imageConverter.convertToASCII(grayscaleBitmap)
                    for (row in asciiArt) {
                        for (value in row) {
                            stringBuilder.append("$value\t")
                        }
                        stringBuilder.append("\n")
                    }
                    textView.text = stringBuilder.toString()
                } else {
                    Log.d("myTag","bitmap is null")
                }
            } catch (e: Exception) {
                Log.e("myTag", "Error reading file: ${e.message}")
            }
        }
    }

