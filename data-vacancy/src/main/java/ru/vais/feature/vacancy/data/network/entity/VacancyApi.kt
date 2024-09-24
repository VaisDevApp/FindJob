package ru.vais.feature.vacancy.data.network.entity

import com.google.gson.annotations.SerializedName

data class VacancyApi(
    @SerializedName("id")
    val id: String,
    @SerializedName("lookingNumber")
    val lookingNumber: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("address")
    val address: AddressApi?,
    @SerializedName("company")
    val company: String,
    @SerializedName("experience")
    val experience: ExperienceApi?,
    @SerializedName("publishedDate")
    val publishedDate: String,
    @SerializedName("isFavorite")
    var isFavorite: Boolean
)