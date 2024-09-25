package ru.vais.feature.favorite.ui.presentation.adapter

sealed class BaseItem {
    data class HeaderUi(val countVacancy: Int) : BaseItem()

    data class VacancyUi(
        val id: String,
        val lookingNumber: Int,
        val isFavorite: Boolean,
        val title: String,
        val town: String?,
        val company: String,
        val previewText: String?,
        val publishedDate: String
    ) : BaseItem() {
    }
}