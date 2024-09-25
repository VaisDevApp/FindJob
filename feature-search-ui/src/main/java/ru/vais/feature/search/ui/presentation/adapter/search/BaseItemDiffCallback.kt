package ru.vais.feature.search.ui.presentation.adapter.search

import androidx.recyclerview.widget.DiffUtil

class BaseItemDiffCallback: DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        val isSameVacancyUi = oldItem is BaseItem.VacancyUi && newItem is BaseItem.VacancyUi && oldItem.id == newItem.id
        val isSameHeaderUi = oldItem is BaseItem.HeaderUi && newItem is BaseItem.HeaderUi && oldItem.titleResId == newItem.titleResId
        val isSameFavoriteHeaderUi = oldItem is BaseItem.FavoriteHeaderUi && newItem is BaseItem.FavoriteHeaderUi && oldItem.titleResId == newItem.titleResId
        val isSameOffersItemUi = oldItem is BaseItem.OffersItemUi && newItem is BaseItem.OffersItemUi && oldItem.offers == newItem.offers
        val isSameFindItemUi = oldItem is BaseItem.FindItemUi && newItem is BaseItem.FindItemUi && oldItem == newItem
        val isSameButtonOnClickItemUi = oldItem is BaseItem.ButtonOnClickItemUi && newItem is BaseItem.ButtonOnClickItemUi && oldItem.countVacancy == newItem.countVacancy
        return isSameVacancyUi || isSameHeaderUi || isSameFavoriteHeaderUi || isSameOffersItemUi || isSameFindItemUi || isSameButtonOnClickItemUi
    }
    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem == newItem
    }
}