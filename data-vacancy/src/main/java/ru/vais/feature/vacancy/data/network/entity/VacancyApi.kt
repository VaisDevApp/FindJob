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
    var isFavorite: Boolean,
    @SerializedName("salary")
    val salary: SalaryIpi,
    @SerializedName("schedules")
    val schedules: List<String>,
    @SerializedName("appliedNumber")
    val appliedNumber: Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("responsibilities")
    val responsibilities: String,
    @SerializedName("questions")
    val questions: List<String>
)