package ru.vais.feature.vacancy.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vais.feature.vacancy.domain.entity.Vacancy
import javax.inject.Inject

class GetFavoriteVacancyUseCase @Inject constructor(private val vacancyPayloadRepository: VacancyPayloadRepository) {
    fun getFavoriteVacancy(): Flow<List<Vacancy>> {
        val flowVacancyList = vacancyPayloadRepository.getVacancyPayload().map { it.vacancyList }
        return flowVacancyList.map { it.filter { it.isFavorite } }
    }
}