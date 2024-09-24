package ru.vais.feature.vacancy.domain.entity

class Vacancy(
    val id: String,
    val lookingNumber: Int,
    var isFavorite: Boolean,
    val title: String,
    val town: String?,
    val company: String,
    val previewText: String?,
    val publishedDate: String
)