package de.leoruland.infovisapp.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.model.Topic
import de.leoruland.infovisapp.model.TopicsChoiceStore

class TopicAdapter(private val topics: List<Topic>) :
    RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var topic: Topic
        private var isChecked = false

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(topic: Topic) {
            this.topic = topic
            val itemTitle: TextView = itemView.findViewById(R.id.topicTitle)
            itemTitle.text = topic.name
        }

        override fun onClick(view: View?) {
            val itemTitle: TextView = itemView.findViewById(R.id.topicTitle)
            val topicCard: CardView = itemView.findViewById(R.id.topic_card)
            isChecked = !isChecked
            if (isChecked) {
                TopicsChoiceStore.addTopic(topic)
                itemTitle.setTextColor(Color.parseColor("#FFFFFF"))
                topicCard.setCardBackgroundColor(Color.parseColor("#A10040"))
            } else {
                TopicsChoiceStore.removeTopic(topic)
                itemTitle.setTextColor(Color.parseColor("#000000"))
                topicCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_topic, parent, false)
        )
    }

    override fun getItemCount() = topics.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topics[position])
    }
}