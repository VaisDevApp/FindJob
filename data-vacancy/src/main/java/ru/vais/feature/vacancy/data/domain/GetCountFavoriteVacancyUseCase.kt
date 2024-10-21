package ru.vais.feature.vacancy.data.domain

import javax.inject.Inject


class GetCountFavoriteVacancyUseCase @Inject constructor(private val vacancyPayloadRepository: VacancyPayloadRepository) {

    fun getCountFavoriteVacancy(): kotlinx.coroutines.flow.Flow<Int> {
        return vacancyPayloadRepository.getCountFavoriteVacancy()
    }
}