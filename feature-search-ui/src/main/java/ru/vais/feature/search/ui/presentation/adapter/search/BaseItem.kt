package ru.vais.feature.search.ui.presentation.adapter.search

import ru.vais.feature.search.ui.presentation.adapter.offer.OfferItem

sealed class BaseItem {
    data class VacancyUi(
        val id: String,
        val lookingNumber: Int,
        var isFavorite: Boolean,
        val title: String,
        val town: String?,
        val company: String,
        val previewText: String?,
        val publishedDate: String
    ) : BaseItem()

    data class HeaderUi(val titleResId: Int) : BaseItem()
    data class FavoriteHeaderUi(val titleResId: Int) : BaseItem()
    data class OffersItemUi(val offers: List<OfferItem>) : BaseItem()
    class FindItemUi() : BaseItem()
    data class ButtonOnClickItemUi(val countVacancy: Int) : BaseItem()
}

