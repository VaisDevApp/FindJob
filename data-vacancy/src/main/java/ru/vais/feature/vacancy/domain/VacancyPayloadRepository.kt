package ru.vais.feature.vacancy.domain

import kotlinx.coroutines.flow.Flow
import ru.vais.feature.vacancy.domain.entity.VacancyPayload

interface VacancyPayloadRepository {
    fun getVacancyPayload(): Flow<VacancyPayload>

    fun getCountFavoriteVacancy(): Flow<Int>

    suspend fun updateFavoriteVacancy(id: String, isFavorite: Boolean)
}
