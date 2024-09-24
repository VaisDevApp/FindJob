package ru.vais.feature.vacancy.data.network.entity

import com.google.gson.annotations.SerializedName

data class VacancyPayloadApi(
    @SerializedName("offers")
    val offers: List<OffersApi>,
    @SerializedName("vacancies")
    val vacancies: List<VacancyApi>
)