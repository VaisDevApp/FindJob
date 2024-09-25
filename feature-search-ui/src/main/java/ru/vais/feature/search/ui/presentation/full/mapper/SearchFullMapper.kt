package ru.vais.feature.search.ui.presentation.full.mapper

import ru.vais.feature.search.ui.presentation.adapter.search.BaseItem
import ru.vais.feature.vacancy.domain.entity.VacancyPayload

object SearchFullMapper {
    fun map(vacancyPayload: VacancyPayload):List<BaseItem>{
        val vacancyList = vacancyPayload.vacancyList.map { vacancy ->
            BaseItem.VacancyUi(
                id = vacancy.id,
                lookingNumber = vacancy.lookingNumber,
                isFavorite = vacancy.isFavorite,
                title = vacancy.title,
                town = vacancy.town,
                company = vacancy.company,
                previewText = vacancy.previewText,
                publishedDate = vacancy.publishedDate
            )
        }
        val resultList = mutableListOf<BaseItem>()
        resultList.add(BaseItem.FavoriteHeaderUi(vacancyList.size))
        resultList.addAll(vacancyList)
        return resultList
    }
}