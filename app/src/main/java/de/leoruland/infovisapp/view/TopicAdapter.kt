package de.leoruland.infovisapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.leoruland.infovisapp.R

class TopicAdapter(private val topics: List<String>) :
    RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(topic: String) {
            val title: TextView = itemView.findViewById(R.id.topicTitle)
            title.text = topic
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_topic, parent, false))
//        return ViewHolder(parent.inflate(R.layout.list_item_topic))
    }

    override fun getItemCount() = topics.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topics[position])
    }
}