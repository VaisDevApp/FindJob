package ru.vais.feature.vacancy.data.network

import retrofit2.http.GET
import ru.vais.feature.vacancy.data.network.entity.VacancyPayloadApi

interface ServerApi {
    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    suspend fun getVacancyPayLoad(): VacancyPayloadApi
}