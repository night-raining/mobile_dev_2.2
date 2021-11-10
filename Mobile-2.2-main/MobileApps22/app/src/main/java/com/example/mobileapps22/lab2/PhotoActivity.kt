package com.example.mobileapps22.lab2

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import com.example.mobileapps22.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PhotoActivity: AppCompatActivity() {
    private val TAG = "PHOTO_ACTIVITY"

    private val IMAGE_CAPTURE_CODE: Int = 1001

    var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivty_photo)
        val btnPhoto = findViewById<Button>(R.id.btn_takePhoto)
        btnPhoto.setOnClickListener {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")
            photoUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            findViewById<ImageView>(R.id.iv_photo).setImageURI(photoUri)
            Log.d(TAG, "Photo uri path - $photoUri")
        }
    }
}