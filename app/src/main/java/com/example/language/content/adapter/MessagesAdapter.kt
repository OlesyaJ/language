package com.example.language.content.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.language.R
import com.example.language.content.data.TextMessage

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    companion object {
        private const val USER = 0
        private const val BOT = 1
    }

    private val items = mutableListOf<TextMessage>()

    fun appendItems(newItem: TextMessage) {
        items.add(0, newItem)
        notifyItemInserted(items.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isUser) {
            USER
        } else {
            BOT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = if (viewType == USER) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_message_user, null, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_message_bot, null, false)
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvMessage = itemView.findViewById<TextView>(R.id.tv_message)

        fun onBind(message: TextMessage) {
            tvMessage.text = message.message
        }
    }
}
