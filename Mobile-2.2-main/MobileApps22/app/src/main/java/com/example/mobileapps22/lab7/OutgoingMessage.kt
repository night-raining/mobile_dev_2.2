package com.example.mobileapps22.lab7

import java.text.SimpleDateFormat
import java.util.*

class OutgoingMessage(text: String, date: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())) : Message(text, date)