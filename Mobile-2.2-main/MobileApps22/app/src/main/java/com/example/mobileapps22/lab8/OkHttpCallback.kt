package com.example.mobileapps22.lab8

import android.os.Handler
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class OkHttpCallback(val okHttpRequester: OkHttpRequester, val responseHandler: Handler) : Callback {
    val TAG = "REQUEST_CALLBACK"

    override fun onFailure(call: Call, e: IOException) {
        Log.d(TAG, "onFailure: ${e.printStackTrace()}")
    }

    override fun onResponse(call: Call, response: Response) {
        Log.d(TAG, "Response code - ${response.code()}")
        this.okHttpRequester.response = response
        responseHandler.sendEmptyMessage(response.code())
    }
}