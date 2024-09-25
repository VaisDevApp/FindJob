package ru.vais.feature.favorite.ui.presentation.adapter

import androidx.recyclerview.widget.DiffUtil

class FavoriteVacancyDiffCallback : DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {

        val isSameVacancyUiItem =
            oldItem is BaseItem.VacancyUi && newItem is BaseItem.VacancyUi && oldItem.id == newItem.id
        val isSameHeaderUiItem =
            oldItem is BaseItem.HeaderUi && newItem is BaseItem.HeaderUi
                    && oldItem.countVacancy == newItem.countVacancy
                    && oldItem.javaClass == newItem.javaClass
        return isSameVacancyUiItem || isSameHeaderUiItem
    }

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem == newItem
    }
}