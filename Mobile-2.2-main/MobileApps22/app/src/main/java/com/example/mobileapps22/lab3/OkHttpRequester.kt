package com.example.lab3httprequests

import android.os.Handler
import okhttp3.*


class OkHttpRequester {

    private val client: OkHttpClient = OkHttpClient()
    var response: Response? = null

    fun httpGet(url: String, getResponseHandler: Handler) {
        val request = Request.Builder()
            .url(url)
            .build()

        val okHttpCallback = OkHttpCallback(this, getResponseHandler)
        client.newCall(request).enqueue(okHttpCallback)
    }
}