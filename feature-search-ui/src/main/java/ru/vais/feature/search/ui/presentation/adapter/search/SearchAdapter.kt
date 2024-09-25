package ru.vais.feature.search.ui.presentation.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.vais.feature.search.ui.R
import ru.vais.feature.search.ui.presentation.adapter.offer.OfferAdapter

class SearchAdapter(private val clickListener: OnClickListener) :
    ListAdapter<BaseItem, ViewHolder>(BaseItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = inflater.inflate(R.layout.item_header, parent, false)
                HeaderViewHolder(view)
            }

            VIEW_TYPE_VACANCY -> {
                val view =
                    inflater.inflate(ru.vais.feature.core.ui.R.layout.item_vacancy, parent, false)
                VacancyViewHolder(view)
            }

            VIEW_TYPE_OFFERS -> {
                val view = inflater.inflate(R.layout.item_offers, parent, false)
                OffersViewHolder(view)
            }

            VIEW_TYPE_FIND_PANEL -> {
                val view = inflater.inflate(R.layout.item_find_panel, parent, false)
                FindPanelViewHolder(view)
            }

            VIEW_TYPE_BUTTON_ONCLICK -> {
                val view = inflater.inflate(R.layout.item_button, parent, false)
                ButtonOnClickViewHolder(view)
            }

            VIEW_TYPE_FAVORITE_HEADER -> {
                val view = inflater.inflate(R.layout.item_favorite_header_panel, parent, false)
                FavoriteHeaderViewHolder(view)
            }

            else -> throw IllegalStateException("View type not found")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BaseItem.HeaderUi -> VIEW_TYPE_HEADER
            is BaseItem.VacancyUi -> VIEW_TYPE_VACANCY
            is BaseItem.OffersItemUi -> VIEW_TYPE_OFFERS
            is BaseItem.ButtonOnClickItemUi -> VIEW_TYPE_BUTTON_ONCLICK
            is BaseItem.FindItemUi -> VIEW_TYPE_FIND_PANEL
            is BaseItem.FavoriteHeaderUi -> VIEW_TYPE_FAVORITE_HEADER
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemSearch = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(itemSearch as BaseItem.HeaderUi)
            is OffersViewHolder -> holder.bind(itemSearch as BaseItem.OffersItemUi)
            is VacancyViewHolder -> holder.bind(itemSearch as BaseItem.VacancyUi)
            is ButtonOnClickViewHolder -> holder.bind(itemSearch as BaseItem.ButtonOnClickItemUi)
            is FavoriteHeaderViewHolder -> holder.bind(itemSearch as BaseItem.FavoriteHeaderUi)
        }
    }

    inner class FindPanelViewHolder(itemView: View) : ViewHolder(itemView)

    inner class ButtonOnClickViewHolder(itemView: View) : ViewHolder(itemView) {
        private val buttonAddVacancyView = itemView.findViewById<TextView>(R.id.button_add_vacancy)

        fun bind(button: BaseItem.ButtonOnClickItemUi) {
            buttonAddVacancyView.setOnClickListener {
                clickListener.onClick()
            }
            buttonAddVacancyView.text = itemView.context.getString(
                R.string.still, itemView.resources.getQuantityString(
                    ru.vais.feature.core.ui.R.plurals.vacancy,
                    button.countVacancy,
                    button.countVacancy
                )
            )
        }
    }

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
                R.string.now_show,
                itemView.resources.getQuantityString(
                    ru.vais.feature.core.ui.R.plurals.lookingNumber,
                    vacancy.lookingNumber,
                    vacancy.lookingNumber
                )
            )
            if (vacancy.isFavorite) {
                isFavoriteView.setImageResource(ru.vais.feature.core.ui.R.drawable.ic_full_heart)
            } else {
                isFavoriteView.setImageResource(ru.vais.feature.core.ui.R.drawable.heart)
            }
            isFavoriteView.setOnClickListener {
                clickListener.onClickChangeFavorite(vacancy)
            }
            titleView.text = vacancy.title
            townView.text = vacancy.town
            companyView.text = vacancy.company
            previewTextView.text = vacancy.previewText
            publishedDateView.text =
                itemView.context.getString(R.string.published, vacancy.publishedDate)
            itemView.setOnClickListener {
                clickListener.onClickToVacancyCard(vacancy)
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {
        private val headerView = itemView.findViewById<TextView>(R.id.header)
        fun bind(headerUi: BaseItem.HeaderUi) {
            headerView.setText(headerUi.titleResId)
        }
    }

    inner class FavoriteHeaderViewHolder(itemView: View) : ViewHolder(itemView) {
        private val favoriteheaderView = itemView.findViewById<TextView>(R.id.count_vacancy_view)
        fun bind(favoriteHeaderUi: BaseItem.FavoriteHeaderUi) {
            favoriteheaderView.text =
                itemView.resources.getQuantityString(
                    ru.vais.feature.core.ui.R.plurals.vacancy,
                    favoriteHeaderUi.titleResId,
                    favoriteHeaderUi.titleResId
                )
        }
    }

    inner class OffersViewHolder(itemView: View) : ViewHolder(itemView) {

        private val offersRecyclerView =
            itemView.findViewById<RecyclerView>(R.id.offers_recycler_view)
        private val offerAdapter = OfferAdapter()

        fun bind(offers: BaseItem.OffersItemUi) {
            offersRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            offersRecyclerView.adapter = offerAdapter
            offerAdapter.submitList(offers.offers)
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_VACANCY = 1
        private const val VIEW_TYPE_OFFERS = 2
        private const val VIEW_TYPE_FIND_PANEL = 3
        private const val VIEW_TYPE_BUTTON_ONCLICK = 4
        private const val VIEW_TYPE_FAVORITE_HEADER = 5
    }

    interface OnClickListener {
        fun onClick()

        fun onClickChangeFavorite(vacancy: BaseItem.VacancyUi)

        fun onClickToVacancyCard(vacancy: BaseItem.VacancyUi)
    }
}