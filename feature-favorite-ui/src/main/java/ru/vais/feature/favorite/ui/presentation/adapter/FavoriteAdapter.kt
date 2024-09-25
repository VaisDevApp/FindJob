package ru.vais.feature.favorite.ui.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.vais.feature.core.ui.DateUtil
import ru.vais.feature.favorite.ui.R
import java.text.SimpleDateFormat

class FavoriteAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<ViewHolder>() {
    private val listItem = mutableListOf<BaseItem>()

    inner class VacancyViewHolder(itemView: View) : ViewHolder(itemView) {
        private val lookingNumberView =
            itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.looking_number)
        private val isFavoriteView =
            itemView.findViewById<ImageView>(ru.vais.feature.core.ui.R.id.is_favorite)
        private val titleView = itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.title)
        private val townView = itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.town)
        private val companyView =
            itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.company)
        private val previewTextView =
            itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.previewText)
        private val publishedDateView =
            itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.publishedDate)
        private val buttonOnClickView =
            itemView.findViewById<TextView>(ru.vais.feature.core.ui.R.id.button_onclick)

        fun bind(vacancy: BaseItem.VacancyUi) {
            lookingNumberView.text = itemView.context.getString(
                R.string.now_show, itemView.resources.getQuantityString(
                    ru.vais.feature.core.ui.R.plurals.lookingNumber,
                    vacancy.lookingNumber,
                    vacancy.lookingNumber
                )
            )
            isFavoriteView.setOnClickListener {
                clickListener.onClick(vacancy)
            }
            titleView.text = vacancy.title
            townView.text = vacancy.town
            companyView.text = vacancy.company
            previewTextView.text = vacancy.previewText
            publishedDateView.text =
                itemView.context.getString(R.string.publish, vacancy.publishedDate)
            if (vacancy.isFavorite) {
                isFavoriteView.setImageResource(ru.vais.feature.core.ui.R.drawable.ic_full_heart)
            } else {
                isFavoriteView.setImageResource(ru.vais.feature.core.ui.R.drawable.heart)
            }
        }
    }

    class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {
        private val headerView = itemView.findViewById<TextView>(R.id.count_vacancy)

        fun bind(headerUi: BaseItem.HeaderUi) {
            headerView.text = itemView.resources.getQuantityString(
                ru.vais.feature.core.ui.R.plurals.vacancy,
                headerUi.countVacancy,
                headerUi.countVacancy
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_VACANCY -> {
                val view =
                    inflater.inflate(ru.vais.feature.core.ui.R.layout.item_vacancy, parent, false)
                VacancyViewHolder(view)
            }

            VIEW_TYPE_HEADER -> {
                val view = inflater.inflate(R.layout.item_count_vacancy, parent, false)
                HeaderViewHolder(view)
            }

            else -> {
                throw IllegalStateException(parent.context.getString(R.string.view_type_not_found))
            }
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listItem[position]) {
            is BaseItem.VacancyUi -> VIEW_TYPE_VACANCY
            is BaseItem.HeaderUi -> VIEW_TYPE_HEADER
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is VacancyViewHolder -> {
                holder.bind(listItem[position] as BaseItem.VacancyUi)
            }

            is HeaderViewHolder -> {
                holder.bind(listItem[position] as BaseItem.HeaderUi)
            }
        }
    }

    fun update(list: List<BaseItem>) {
        listItem.clear()
        listItem.addAll(list)
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClick(vacancy: BaseItem.VacancyUi)
    }

    companion object {
        private const val VIEW_TYPE_VACANCY = 0
        private const val VIEW_TYPE_HEADER = 1
    }
}