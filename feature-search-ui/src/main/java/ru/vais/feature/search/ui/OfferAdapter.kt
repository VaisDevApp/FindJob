package ru.vais.feature.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class OfferAdapter : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    private val listItem = mutableListOf<OfferItem>()

    class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idOffer = itemView.findViewById<ImageView>(R.id.id_offer)
        private val titleOffer = itemView.findViewById<TextView>(R.id.title_offer)
        private val buttonOffer = itemView.findViewById<TextView>(R.id.button_offer)
        fun bind(offer: OfferItem) {
            when (offer.id) {
                "near_vacancies" -> idOffer.setImageResource(R.drawable.ellipse_blue)
                "level_up_resume" -> idOffer.setImageResource(R.drawable.small_star)
                "temporary_job" -> idOffer.setImageResource(R.drawable.ic_list)
                else -> idOffer.setImageResource(R.drawable.ellipse_13)
            }
            titleOffer.text = offer.title
            val needShowButton = offer.buttonText != null
            if (needShowButton) {
                buttonOffer.isVisible = true
                titleOffer.setLines(2)
                buttonOffer.text = offer.buttonText
            } else {
                buttonOffer.isVisible = false
                titleOffer.setLines(3)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.item_offer, parent, false)
        val offerViewHolder = OfferViewHolder(view)
        return offerViewHolder
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offerObject = listItem[position]
        holder.bind(offerObject)
    }

    fun update(list: List<OfferItem>) {
        listItem.clear()
        listItem.addAll(list)
        notifyDataSetChanged()
    }
}