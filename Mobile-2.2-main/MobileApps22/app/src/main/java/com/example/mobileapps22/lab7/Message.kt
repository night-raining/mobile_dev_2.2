package com.example.mobileapps22.lab7

import java.text.SimpleDateFormat
import java.util.*

open class Message(val text: String, val date: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))
