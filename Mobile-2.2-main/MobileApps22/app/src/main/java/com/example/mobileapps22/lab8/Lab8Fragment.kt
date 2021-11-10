package com.example.mobileapps22.lab8

import android.content.Intent
import android.os.*
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobileapps22.R
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

val TAG = "FRAGMENT_JWT"
private val HTTP_CODE_UNAUTHORIZED = 401

private const val URL_TOKEN = "http://192.168.0.100:5000/auth"
private const val URL_SECURED = "http://192.168.0.100:5000/protected"
//private const val URL_TOKEN = "https://192.168.0.100:5000/auth"
//private const val URL_SECURED = "https://192.168.0.100:5000/protected"

private lateinit var currentToken: String

/**
 * A simple [Fragment] subclass.
 * Use the [Lab8Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lab8Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var requester: OkHttpRequester // TODO: 07.06.2021 null check

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab8, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        requester = OkHttpRequester(getSSLSocketFactory()) // For secure ssl connection
        requester = OkHttpRequester() // For http connection
//        requester.sslSocketFactory = getSSLSocketFactory() // Not working option for ssl factory set up
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build() // Removes restrictions for networking in main thread?
        StrictMode.setThreadPolicy(policy)
        val securedDataResponseHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                Log.d(TAG, "Auth response")
                val jsonResponse = JSONObject(requester.response?.body()!!.string())
                Log.d(TAG, "$jsonResponse")
                val rTime = jsonResponse.get("time").toString()
                val rStaticText = jsonResponse.get("text").toString()
                val rImage = Base64.decode(jsonResponse.get("image").toString().toByteArray(), Base64.DEFAULT)
                val dataPreviewIntent = Intent(context, ResponseDataActivity::class.java)
                dataPreviewIntent.putExtra("time", rTime)
                dataPreviewIntent.putExtra("text", rStaticText)
                dataPreviewIntent.putExtra("image", rImage)
                startActivity(dataPreviewIntent)
            }
        }
        val emptyAuthDataHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val responseBody = requester.response?.body()!!
                val jsonResponse = JSONObject(responseBody.string())
                val errorDescription = jsonResponse.get("description").toString()
                view.findViewById<TextView>(R.id.tv_jwtToken).text = errorDescription
            }
        }
        val jwtTokenResponseHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                Log.d(TAG, "JWT response code - ${requester.response?.code()}")
                val responseBody = requester.response?.body()!!
                val jsonResponse = JSONObject(responseBody.string())
                if (msg.what == HTTP_CODE_UNAUTHORIZED) {
                    val errorDescription = jsonResponse.get("description").toString()
                    view.findViewById<TextView>(R.id.tv_jwtToken).text = errorDescription
                } else {
                    val token = jsonResponse.get("access_token")
                    currentToken = token.toString()
                    view.findViewById<Button>(R.id.btn_securedDataRequest).isEnabled = true
                    view.findViewById<TextView>(R.id.tv_jwtToken).text = currentToken
                }
            }
        }

        view.findViewById<Button>(R.id.btn_securedDataRequest).setOnClickListener {
            requester.httpGet(
                    URL_SECURED,
                    mapOf("Authorization" to "JWT $currentToken"),
                    securedDataResponseHandler
            )
        }

        view.findViewById<Button>(R.id.btn_jwt_request).setOnClickListener {
            val login = view.findViewById<TextInputEditText>(R.id.ti_formLogin).text.toString()
            val pass = view.findViewById<TextInputEditText>(R.id.ti_formPassword).text.toString()
            val jsonObject = JSONObject()
            jsonObject.put("username", login)
            jsonObject.put("password", pass)
            val requestBody = jsonObject.toString()
            requester.httpPost(URL_TOKEN, requestBody, jwtTokenResponseHandler)
        }

        view.findViewById<Button>(R.id.btn_jwt_emptyRequest).setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("username", "none")
            jsonObject.put("password", "none")
            val requestBody = jsonObject.toString()
            requester.httpPost(URL_TOKEN, requestBody, emptyAuthDataHandler)
        }
    }

    private fun getSSLSocketFactory(): SSLSocketFactory? {
        return try {
            val cf: CertificateFactory
            cf = CertificateFactory.getInstance("X.509")
            val ca: Certificate
            val cert: InputStream = resources.openRawResource(R.raw.flaskws)
            ca = cf.generateCertificate(cert)
            cert.close()
            val keyStoreType: String = KeyStore.getDefaultType()
            val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, charArrayOf('1', '2', '3', '4'))
            keyStore.setCertificateEntry("ca", ca)
            val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
            val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)
            val sslContext: SSLContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.getTrustManagers(), null)
            sslContext.getSocketFactory()
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Lab8Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Lab8Fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}