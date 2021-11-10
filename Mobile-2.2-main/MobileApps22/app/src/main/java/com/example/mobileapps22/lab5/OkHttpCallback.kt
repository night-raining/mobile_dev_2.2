package com.example.mobileapps22.lab5

import android.os.Handler
import android.os.Message
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class OkHttpCallback(val okHttpRequester: OkHttpRequester, val getResponseHandler: Handler) : Callback {
    val TAG = "REQUEST_CALLBACK"

    override fun onFailure(call: Call, e: IOException) {
        e.printStackTrace()
    }

    override fun onResponse(call: Call, response: Response) {
        Log.d(TAG, "Response code - ${response.code()}")
        if (response.isSuccessful) {
            this.okHttpRequester.response = response
            getResponseHandler.sendEmptyMessage(1)
        }
    }
}