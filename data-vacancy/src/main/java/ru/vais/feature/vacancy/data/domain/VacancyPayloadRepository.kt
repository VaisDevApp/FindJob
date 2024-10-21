package ru.vais.feature.vacancy.data.domain

import kotlinx.coroutines.flow.Flow
import ru.vais.feature.vacancy.data.domain.entity.Vacancy
import ru.vais.feature.vacancy.data.domain.entity.VacancyPayload

interface VacancyPayloadRepository {
    fun getVacancyPayload(): Flow<VacancyPayload>

    fun getCountFavoriteVacancy(): Flow<Int>

    suspend fun updateFavoriteVacancy(id: String, isFavorite: Boolean)

    suspend fun getVacancyById(id: String): Vacancy
}
