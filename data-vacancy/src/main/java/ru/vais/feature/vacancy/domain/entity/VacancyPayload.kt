package ru.vais.feature.vacancy.domain.entity

class VacancyPayload(
    val vacancyList: List<Vacancy>,
    val offerList: List<Offer>
)