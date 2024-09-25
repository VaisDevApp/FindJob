package ru.vais.feature.vacancy.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Offer")
class OfferDb(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "idFromServer")
    val idFromServer: String?,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "buttonText")
    val buttonText: String?,

    @ColumnInfo(name = "link")
    val link: String
)