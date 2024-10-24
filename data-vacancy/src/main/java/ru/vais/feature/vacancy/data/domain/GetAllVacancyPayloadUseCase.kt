package ru.vais.feature.vacancy.data.domain

import kotlinx.coroutines.flow.Flow
import ru.vais.feature.vacancy.data.domain.entity.VacancyPayload
import javax.inject.Inject

class GetAllVacancyPayloadUseCase @Inject constructor(private val vacancyPayloadRepository: VacancyPayloadRepository) {
    fun getAllVacancyPayload(): Flow<VacancyPayload>{
        return vacancyPayloadRepository.getVacancyPayload()
    }
}