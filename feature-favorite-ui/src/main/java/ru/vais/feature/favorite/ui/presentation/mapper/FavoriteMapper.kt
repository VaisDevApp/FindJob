package ru.vais.feature.favorite.ui.presentation.mapper

import ru.vais.feature.core.ui.DateUtil
import ru.vais.feature.favorite.ui.presentation.adapter.BaseItem
import ru.vais.feature.vacancy.domain.entity.Vacancy

object FavoriteMapper {
    fun map(vacancyList: List<Vacancy>):List<BaseItem> {
        val headerUi = BaseItem.HeaderUi(vacancyList.size)
        val listForFlow = mutableListOf<BaseItem>()
        listForFlow.add(headerUi)
        val vacancyList = vacancyList.map {vacancy ->
            BaseItem.VacancyUi(
                vacancy.id,
                vacancy.lookingNumber,
                vacancy.isFavorite,
                vacancy.title,
                vacancy.town,
                vacancy.company,
                vacancy.previewText,
                DateUtil.convert(vacancy.publishedDate)
            )
        }
        listForFlow.addAll(vacancyList)
        return listForFlow
    }
}