package ru.vais.feature.vacancy.data.domain

import javax.inject.Inject

class UpdateFavoriteVacancyUseCase @Inject constructor(private val vacancyPayloadRepository: VacancyPayloadRepository) {

    suspend fun updateFavoriteVacancy(id: String, isFavorite: Boolean) {
        vacancyPayloadRepository.updateFavoriteVacancy(id, isFavorite)
    }
}