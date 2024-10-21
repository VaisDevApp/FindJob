package ru.vais.feature.vacancy.data.domain.entity

import androidx.room.ColumnInfo

class Offer(
    val id: String?,
    val title: String,
    val buttonText: String?,
    val link: String
)