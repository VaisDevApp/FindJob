package ru.vais.feature.favorite.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.text.SimpleDateFormat

class FavoriteAdapter(val clickListener: ClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listItem = mutableListOf<BaseFavoriteItem>()
    private val dateFormatOutput = SimpleDateFormat("dd MMMM")
    private val dateFormatInput = SimpleDateFormat("yyyy-MM-dd")

    inner class VacancyViewHolder(itemView: View) : ViewHolder(itemView) {
        private val lookingNumberView = itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.looking_number)
        private val isFavoriteView = itemView.findViewById<ImageView>(ru.vais.feature.core.ui.R.id.is_favorite)
        private val titleView = itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.title)
        private val townView = itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.town)
        private val companyView = itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.company)
        private val previewTextView = itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.previewText)
        private val publishedDateView = itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.publishedDate)
        private val buttonOnClickView = itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.button_onclick)

        fun bind(vacancy: BaseFavoriteItem.VacancyUi) {
            lookingNumberView.text = "Сейчас просматривает ${itemView.resources.getQuantityString(ru.vais.feature.core.ui.R.plurals.lookingNumber, vacancy.lookingNumber, vacancy.lookingNumber)}"
            isFavoriteView.setOnClickListener {
                clickListener.onClick(vacancy)
            }
            titleView.text = vacancy.title
            townView.text = vacancy.town
            companyView.text = vacancy.company
            previewTextView.text = vacancy.previewText
            val date = dateFormatInput.parse(vacancy.publishedDate)
            publishedDateView.text = "Опубликовано ${dateFormatOutput.format(date)}"
            if (vacancy.isFavorite) {
                isFavoriteView.setImageResource(ru.vais.feature.core.ui.R.drawable.ic_full_heart)
            } else {
                isFavoriteView.setImageResource(ru.vais.feature.core.ui.R.drawable.heart)
            }
        }
    }

    class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {
        private val headerView = itemView.findViewById<TextView>(R.id.count_vacancy)

        fun bind(headerUi: BaseFavoriteItem.HeaderUi) {
            headerView.text = "${
                itemView.resources.getQuantityString(
                    ru.vais.feature.core.ui.R.plurals.vacancy,
                    headerUi.countVacancy,
                    headerUi.countVacancy
                )
            }"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_VACANCY -> {
                val view = inflater.inflate(ru.vais.feature.core.ui.R.layout.item_vacancy, parent, false)
                VacancyViewHolder(view)
            }

            VIEW_TYPE_HEADER -> {
                val view = inflater.inflate(R.layout.item_count_vacancy, parent, false)
                HeaderViewHolder(view)
            }

            else -> {
                throw IllegalStateException("View type not found")
            }
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listItem[position]) {
            is BaseFavoriteItem.VacancyUi -> VIEW_TYPE_VACANCY
            is BaseFavoriteItem.HeaderUi -> VIEW_TYPE_HEADER
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is VacancyViewHolder -> {
                holder.bind(listItem[position] as BaseFavoriteItem.VacancyUi)
            }

            is HeaderViewHolder -> {
                holder.bind(listItem[position] as BaseFavoriteItem.HeaderUi)
            }
        }
    }

    fun update(list: List<BaseFavoriteItem>) {
        listItem.clear()
        listItem.addAll(list)
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClick(vacancy: BaseFavoriteItem.VacancyUi)
    }

    companion object {
        private const val VIEW_TYPE_VACANCY = 0
        private const val VIEW_TYPE_HEADER = 1
    }
}