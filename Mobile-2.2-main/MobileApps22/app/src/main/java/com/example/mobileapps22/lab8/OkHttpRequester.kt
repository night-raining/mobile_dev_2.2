package com.example.mobileapps22.lab8

import android.os.Handler
import android.util.Log
import okhttp3.*
import java.security.KeyStore
import java.util.*
import javax.net.ssl.*


class OkHttpRequester(sslSocketFactory: SSLSocketFactory? = null) {

//    var sslSocketFactory: SSLSocketFactory? = null
//    private var client: OkHttpClient = OkHttpClient.Builder()
//            .sslSocketFactory(sslSocketFactory)
//            .build()
    private var client: OkHttpClient
    var response: Response? = null
//    var certIS: InputStream
    val JSON = MediaType.parse("application/json; charset=utf-8")
    val WS_HOST = "192.168.0.100"


//    fun httpGet(url: String, responseHandler: Handler) {
//        val requestBuilder = Request.Builder()
//            .url(url)
//            .build()
//        val okHttpCallback = OkHttpCallback(this, responseHandler)
//        client.newCall(requestBuilder).enqueue(okHttpCallback)
//    }

    init {
        if (sslSocketFactory!=null){
            val trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                ("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers))
            }
            val trustManager: X509TrustManager = trustManagers[0] as X509TrustManager

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
//        val sslSocketFactory = sslContext.socketFactory

            client = OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .build()
        } else {
            client = OkHttpClient.Builder().build()
        }

    }

    fun httpGet(url: String, headers: Map<String, String>, responseHandler: Handler) {
        var requestBuilder = Request.Builder()
            .url(url)
        for ((key, value) in headers) {
            requestBuilder.addHeader(key, value)
        }
            val request = requestBuilder.build()
        val okHttpCallback = OkHttpCallback(this, responseHandler)
        client.newCall(request).enqueue(okHttpCallback)
    }

//    fun httpPost(url: String, requestBody: RequestBody, responseHandler: Handler) {
//        val request = Request.Builder()
//            .url(url)
//            .post(requestBody)
//            .header("Accept", "application/json")
//            .header("Accept-Encoding", "gzip, deflate")
//            .header("Connection", "keep-alive")
//            .header("Content-Type", "application/json")
//            .header("Host", "192.168.0.103:5000")
//            .build()
//        val okHttpCallback = OkHttpCallback(this, responseHandler)
//        client.newCall(request).enqueue(okHttpCallback)
//        Log.d(TAG, "Post request send")
//    }

    fun httpPost(url: String, jsonStringBody: String, responseHandler: Handler) {
        val requestBody = RequestBody.create(JSON, jsonStringBody)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .header("Accept", "application/json")
            .header("Accept-Encoding", "gzip, deflate")
            .header("Connection", "keep-alive")
            .header("Content-Type", "application/json")
            .header("Host", WS_HOST)
            .build()
        val okHttpCallback = OkHttpCallback(this, responseHandler)
        client.newCall(request).enqueue(okHttpCallback)
        Log.d(TAG, "Post request send")
    }
}