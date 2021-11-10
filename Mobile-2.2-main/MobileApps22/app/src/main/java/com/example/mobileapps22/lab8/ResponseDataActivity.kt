package com.example.mobileapps22.lab8

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.mobileapps22.R

class ResponseDataActivity : AppCompatActivity() {
    val TAG = "ACTIVITY_RESPONSE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response_data)
        val time = intent.getStringExtra("time")
        val text = intent.getStringExtra("text")
        val imageByteArray = intent.getByteArrayExtra("image")?.let { byteArrayToBitmap(it) }
        findViewById<TextView>(R.id.tv_secData_text).text = text
        findViewById<TextView>(R.id.tv_secData_time).text = time
        findViewById<ImageView>(R.id.iv_secData_image).setImageBitmap(imageByteArray)
    }

    private fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }
}