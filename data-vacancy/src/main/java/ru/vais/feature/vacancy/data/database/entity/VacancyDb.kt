package ru.vais.feature.vacancy.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Vacancy")
data class VacancyDb (

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "lookingNumber")
    val lookingNumber: Int,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "town")
    val town: String?,

    @ColumnInfo(name = "company")
    val company: String,

    @ColumnInfo(name = "previewText")
    val previewText: String?,

    @ColumnInfo(name = "publishedDate")
    val publishedDate: String
)