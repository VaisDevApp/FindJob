package ru.vais.feature.favorite.ui

sealed class BaseFavoriteItem {
    class HeaderUi(val countVacancy: Int) : BaseFavoriteItem()

    data class VacancyUi(
        val id: String,
        val lookingNumber: Int,
        val isFavorite: Boolean,
        val title: String,
        val town: String?,
        val company: String,
        val previewText: String?,
        val publishedDate: String
    ) : BaseFavoriteItem()
}