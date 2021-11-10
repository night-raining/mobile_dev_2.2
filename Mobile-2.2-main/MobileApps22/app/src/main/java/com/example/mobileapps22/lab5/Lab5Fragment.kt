package com.example.mobileapps22.lab5

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapps22.R
import org.json.JSONArray
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

val TAG = "FRAGMENT_API_LIST"

private const val API_URL = "https://www.breakingbadapi.com/api/quotes"

/**
 * A simple [Fragment] subclass.
 * Use the [Lab5Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lab5Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val requester = OkHttpRequester()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val getResponseHandler = object: Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val mapper = jacksonObjectMapper()
                val bodyResponse = requester.response!!.body()!!
//                view.findViewById<TextView>(R.id.tv_response).text = bodyResponse.string()
//                val jsonResponse: JSONArray = JSONArray(bodyResponse)
//                for (i in 0..jsonResponse.length()) {
//                    Log.d(TAG, "obj $i - ${jsonResponse.getJSONObject(i)}")
//                }
                val quotesList: List<Quote> = mapper.readValue(bodyResponse.string())
//                for (quote in quotesList) {
//                    Log.d(TAG, "$quote")
//                }
                val recyclerViewQuotes = view.findViewById<RecyclerView>(R.id.rv_quotes)
                recyclerViewQuotes.adapter = QuoteAdapter(quotesList)
                recyclerViewQuotes.layoutManager = LinearLayoutManager(view.context)
                recyclerViewQuotes.setHasFixedSize(true)
            }
        }
        requester.httpGet(API_URL, getResponseHandler)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Lab5Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Lab5Fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}