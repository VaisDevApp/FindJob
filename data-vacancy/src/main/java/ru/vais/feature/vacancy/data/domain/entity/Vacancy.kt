package ru.vais.feature.vacancy.data.domain.entity

class Vacancy(
    val id: String,
    val lookingNumber: Int,
    var isFavorite: Boolean,
    val title: String,
    val town: String?,
    val company: String,
    val previewText: String?,
    val publishedDate: String,
    val salary: String,
    val schedules: String,
    val appliedNumber: Int,
    val description: String?,
    val responsibilities: String,
    val questions: List<String>
)