package ru.vais.feature.vacancy.data.network.entity

import com.google.gson.annotations.SerializedName


data class OffersApi (
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("button")
    val button: ButtonApi?,
    @SerializedName("link")
    val link: String
)