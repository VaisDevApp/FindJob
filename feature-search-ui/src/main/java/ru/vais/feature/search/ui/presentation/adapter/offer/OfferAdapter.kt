package ru.vais.feature.search.ui.presentation.adapter.offer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.vais.feature.search.ui.R

class OfferAdapter (private val clickListener: ClickListener) : ListAdapter<OfferItem, OfferAdapter.OfferViewHolder>(OfferItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.item_offer, parent, false)
        val offerViewHolder = OfferViewHolder(view)
        return offerViewHolder
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offerObject = getItem(position)
        holder.bind(offerObject)
    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idOffer = itemView.findViewById<ImageView>(R.id.id_offer)
        private val titleOffer = itemView.findViewById<TextView>(R.id.title_offer)
        private val buttonOffer = itemView.findViewById<TextView>(R.id.button_offer)
        fun bind(offer: OfferItem) {
            when (offer.id) {
                NEAR_VACANCIES -> {
                    idOffer.setImageResource(R.drawable.ic_location)
                    idOffer.setBackgroundResource(R.drawable.ellipse_blue)
                }

                LEVEL_UP_RESUME -> {
                    idOffer.setImageResource(R.drawable.small_star)
                    idOffer.setBackgroundResource(R.drawable.ellipse_13)
                }

                TEMPORARY_JOB -> {
                    idOffer.setImageResource(R.drawable.ic_list)
                    idOffer.setBackgroundResource(R.drawable.ellipse_13)
                }

                else -> {
                    idOffer.setImageDrawable(null)
                    idOffer.setBackgroundDrawable(null)
                }
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
            itemView.setOnClickListener {
                clickListener.onClickToOffer(offer.link)
            }
        }
    }

    interface ClickListener {
        fun onClickToOffer(link: String)
    }

    companion object {
        private const val NEAR_VACANCIES = "near_vacancies"
        private const val LEVEL_UP_RESUME = "level_up_resume"
        private const val TEMPORARY_JOB = "temporary_job"
    }
}