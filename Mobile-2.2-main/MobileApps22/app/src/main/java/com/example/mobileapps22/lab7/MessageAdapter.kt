package com.example.mobileapps22.lab7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapps22.R

class MessageAdapter(private val messageList: List<Message>): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private val MESSAGE_IN = 1
    private val MESSAGE_OUT = 2

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvText: TextView = itemView.findViewById(R.id.tv_message_text)
        val tvDate: TextView = itemView.findViewById(R.id.tv_message_date)
    }

    override fun getItemViewType(position: Int): Int = if (messageList[position] is IncomingMessage) MESSAGE_IN else MESSAGE_OUT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType==MESSAGE_OUT) {
            MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message_outgoing, parent, false))
        } else MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message_incoming, parent, false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentItem = messageList[position]
        holder.tvText.text = currentItem.text
        holder.tvDate.text = currentItem.date.toString()
    }

    override fun getItemCount(): Int = messageList.size
}