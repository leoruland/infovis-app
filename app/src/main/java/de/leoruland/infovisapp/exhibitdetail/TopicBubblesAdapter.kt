package de.leoruland.infovisapp.exhibitdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.data.Topic

class TopicBubblesAdapter(private val topics: List<Topic>) :
    RecyclerView.Adapter<TopicBubblesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var topic: Topic
        private lateinit var itemTitle: TextView

        fun bind(topic: Topic) {
            this.topic = topic
            itemTitle = itemView.findViewById(R.id.topic_item_title)
            itemTitle.text = topic.name
        }

        override fun onClick(view: View?) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_topicbubble, parent, false)
        )
    }

    override fun getItemCount() = topics.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topics[position])
    }
}