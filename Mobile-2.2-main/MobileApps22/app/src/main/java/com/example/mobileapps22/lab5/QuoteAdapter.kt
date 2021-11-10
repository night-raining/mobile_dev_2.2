package com.example.mobileapps22.lab5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapps22.R

class QuoteAdapter(private val quoteList: List<Quote>): RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    class QuoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val quoteId: TextView = itemView.findViewById(R.id.tv_quote_id)
        val quoteText: TextView = itemView.findViewById(R.id.tv_quote_text)
        val quoteAuthor: TextView = itemView.findViewById(R.id.tv_quote_author)
        val quoteSeries: TextView = itemView.findViewById(R.id.tv_quote_series)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val currentItem = quoteList[position]
        holder.quoteId.text = currentItem.quote_id.toString()
        holder.quoteText.text = currentItem.quote
        holder.quoteAuthor.text = currentItem.author
        holder.quoteSeries.text = currentItem.series
    }

    override fun getItemCount() = quoteList.size

}