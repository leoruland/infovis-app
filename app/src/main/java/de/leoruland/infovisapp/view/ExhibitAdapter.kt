package de.leoruland.infovisapp.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.model.Exhibit
import de.leoruland.infovisapp.viewmodel.ExhibitChoiceStateHolder

class ExhibitAdapter(private val exhibits: List<Exhibit>) :
    RecyclerView.Adapter<ExhibitAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var exhibit: Exhibit

        init {
            val itemTitle: TextView = itemView.findViewById(R.id.exhibitTitle)
            val topicCard: CardView = itemView.findViewById(R.id.exhibit_card)
            itemTitle.setTextColor(Color.parseColor("#000000"))
            topicCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            itemView.setOnClickListener(this)
        }

        fun bind(exhibit: Exhibit) {
            this.exhibit = exhibit
            val itemTitle: TextView = itemView.findViewById(R.id.exhibitTitle)
            itemTitle.text = String.format("%s %s", exhibit.id, exhibit.name)
//                exhibit.name
        }

        override fun onClick(view: View?) {
            ExhibitChoiceStateHolder.setExhibit(exhibit)
            itemView.findNavController().navigate(R.id.action_ChoiceExhibitFragment_to_DetailExhibitFragment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_exhibit, parent, false)
        )
    }

    override fun getItemCount() = exhibits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exhibits[position])
    }
}