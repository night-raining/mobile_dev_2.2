package com.example.mobileapps22.lab3

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lab3httprequests.OkHttpRequester
import com.example.mobileapps22.R
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Lab3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lab3Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val TAG = "FRAGMENT_REQUESTS"
    val url: String = "https://bdu.fstec.ru/threat"
    val requester = OkHttpRequester()

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
        return inflater.inflate(R.layout.fragment_lab3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requestRequiredPermissions()
        val tvResponse: TextView = view.findViewById(R.id.tv_response)
        val tvParsed1: TextView = view.findViewById(R.id.tv_parsedValue)
        tvResponse.canScrollVertically(1)
        val getResponseHandler = object: Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val bodyResponse = requester.response!!.body()!!.string()
                tvResponse.text = bodyResponse
//                tvParsed1.text = requester.response!!.header("expires")
                tvParsed1.text = parseValue(bodyResponse)
            }
        }
        view.findViewById<Button>(R.id.btn_sendRequest).setOnClickListener {
            requester.httpGet(url, getResponseHandler)
            it.isEnabled = false
        }
    }

    private fun parseValue(body: String): String {
        val doc: Document = Jsoup.parse(body)
        val value: String = doc.getElementsByClass("summary pull-right hidden-xs").text().split("\\s".toRegex())[6]
        Log.d(TAG, "value - $value")
        return value
    }

    private fun requestRequiredPermissions() {
        if (context?.checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            val requestedPermissions = arrayOf(android.Manifest.permission.INTERNET)
            requestPermissions(requestedPermissions, 1000)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Lab3Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Lab3Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}