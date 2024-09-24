package ru.vais.feature.vacancy.data.network.entity

import com.google.gson.annotations.SerializedName

data class ExperienceApi (
    @SerializedName("previewText")
    val previewText: String?
)