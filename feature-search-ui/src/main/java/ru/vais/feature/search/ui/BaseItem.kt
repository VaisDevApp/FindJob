package ru.vais.feature.search.ui

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

    class HeaderUi(val titleResId: Int) : BaseItem()

    class OffersItemUi(
        val offers: List<OfferItem>
    ) : BaseItem()

    class FindItemUi() : BaseItem()

    class ButtonOnClickItemUi(val countVacancy: Int) : BaseItem()

}

class OfferItem(
    val id: String?,
    val title: String,
    val buttonText: String?
)