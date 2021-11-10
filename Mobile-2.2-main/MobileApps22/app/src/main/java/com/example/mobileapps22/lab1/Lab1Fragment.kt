package com.example.mobileapps22.lab1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.mobileapps22.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Lab1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lab1Fragment : Fragment(), View.OnLongClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_lab1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnHi = view.findViewById<Button>(R.id.btn_hi)
        btnHi?.setOnClickListener {
            Toast.makeText(view.context, "Hi, i'm button!", Toast.LENGTH_SHORT).show()
        }
        val btnLongPress = view.findViewById<Button>(R.id.btn_longpress)
        btnLongPress.setOnLongClickListener(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Lab1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Lab1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onLongClick(v: View?): Boolean {
        Toast.makeText(view?.context, "Long pressed", Toast.LENGTH_SHORT).show()
        return true
    }
}