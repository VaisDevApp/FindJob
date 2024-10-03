package ru.vais.feature.vacancy.data.network.domain

import ru.vais.feature.vacancy.data.network.domain.entity.Vacancy
import javax.inject.Inject

class GetVacancyByIdUseCase @Inject constructor(private val vacancyPayloadRepository: VacancyPayloadRepository) {

    suspend fun getVacancyById(id: String): Vacancy {
        return vacancyPayloadRepository.getVacancyById(id)
    }
}