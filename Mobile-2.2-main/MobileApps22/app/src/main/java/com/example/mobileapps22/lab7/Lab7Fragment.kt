package com.example.mobileapps22.lab7

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapps22.R
import okhttp3.*
import okio.ByteString
import java.util.concurrent.RejectedExecutionException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

val TAG = "FRAGMENT_WS"
//val WS_HOST = "ws://192.168.0.100:5000"
val WS_HOST = "https://echo-ws.herokuapp.com/echo"
//val WS_HOST = "ws://echo"
/**
 * A simple [Fragment] subclass.
 * Use the [Lab7Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lab7Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private var messageArrayList: ArrayList<Message> = ArrayList()

    lateinit var ws: WebSocket

    lateinit var rvChat: RecyclerView

    private val NORMAL_CLOSURE_STATUS = 1000

    inner class ChatWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response?) {
            Log.d(TAG, "ws onOpen")
            ws = webSocket
            ws.send("Welcome to chat!")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d(TAG, "received msg string")
            addChatMessage(IncomingMessage(text))
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.d(TAG, "received byte string")
            addChatMessage(IncomingMessage(bytes.base64()))
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d(TAG,"Closing: $code $reason")
            addChatMessage(Message("End of chat session"))
            webSocket.close(code, "$code")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
            t.printStackTrace()
        }
    }

    fun addChatMessage(msg: Message) {
        messageArrayList.add(msg)
        rvChat.adapter?.notifyItemInserted(messageArrayList.size)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val request = Request.Builder()
            .url(WS_HOST)
            .build()
        val listener = ChatWebSocketListener()
        try {
            ws = okHttpClient.newWebSocket(request, listener)
        } catch (ex: RejectedExecutionException) {
            ws.close(1001, "Exit")
        }
        // Trigger shutdown of the dispatcher's executor so this process can
        // exit cleanly.
        okHttpClient.dispatcher().executorService().shutdown() // ???

        rvChat = view.findViewById(R.id.rv_chat)
        rvChat.adapter = MessageAdapter(messageArrayList)
        rvChat.layoutManager = LinearLayoutManager(view.context)
        rvChat.setHasFixedSize(true)

        view.findViewById<Button>(R.id.btn_chat_send).setOnClickListener {
            val msg = view.findViewById<EditText>(R.id.et_chat).text.toString()
            sendNewMessage(OutgoingMessage(msg))
        }
        view.findViewById<Button>(R.id.btn_chat_close).setOnClickListener {
            ws.close(NORMAL_CLOSURE_STATUS, "Goodbye!")
            addChatMessage(Message("End of chat session"))
        }
    }

    private fun sendNewMessage(outgoingMessage: OutgoingMessage) {
        messageArrayList.add(outgoingMessage)
        rvChat.adapter?.notifyItemInserted(messageArrayList.size)
        ws.send(outgoingMessage.text)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab7, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Lab7Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Lab7Fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}