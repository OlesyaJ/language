package com.example.language.onboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.language.R
import com.example.language.onboarding.data.TopicItem

class TopicAdapter : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    private val items = mutableListOf<TopicItem>()
    private val selectedItems = mutableListOf<TopicItem>()

    fun setItems(newItems: List<TopicItem>) {
        items.clear()
        items.addAll(newItems)
        selectedItems.removeAll { selected ->
            newItems.none { new ->
                new.topicName == selected.topicName
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_topic, null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivImage = itemView.findViewById<ImageView>(R.id.iv_topic)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_topic_title)

        fun onBind(topicItem: TopicItem) {
            Glide.with(itemView)
                .load(topicItem.imageUrl)
                .centerCrop()
                .into(ivImage)
            tvTitle.text = topicItem.topicName
        }
    }
}
