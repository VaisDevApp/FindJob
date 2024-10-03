package ru.vais.feature.vacancy.data.network.domain.entity

class VacancyPayload(
    val vacancyList: List<Vacancy>,
    val offerList: List<Offer>
)