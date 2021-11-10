package com.example.mobileapps22.lab2

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.mobileapps22.R

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val videoView: VideoView = findViewById(R.id.vidv_video)
        val mediaController = MediaController(this) // applicationContext != this ?!
        val path = "android.resource://$packageName/${R.raw.cat}"
        videoView.setVideoURI(Uri.parse(path))
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
        videoView.setOnCompletionListener {
            it.start()
        }
    }
}