package ru.vais.feature.vacancy.data.network.entity

import com.google.gson.annotations.SerializedName

data class AddressApi(
    @SerializedName("town")
    val town: String?
)