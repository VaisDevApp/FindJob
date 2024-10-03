package ru.vais.feature.search.ui.presentation.main.mapper

import ru.vais.feature.core.ui.DateUtil
import ru.vais.feature.search.ui.R
import ru.vais.feature.search.ui.presentation.adapter.search.BaseItem
import ru.vais.feature.search.ui.presentation.adapter.offer.OfferItem
import ru.vais.feature.vacancy.data.network.domain.entity.VacancyPayload

object SearchMapper {
    fun map(vacancyPayload: VacancyPayload): List<BaseItem> {
        val resultList = mutableListOf<BaseItem>()
        resultList.add(BaseItem.FindItemUi())
        if (vacancyPayload.offerList.isNotEmpty()){
            val offerList = vacancyPayload.offerList.map { offer ->
                OfferItem(
                    id = offer.id,
                    title = offer.title,
                    buttonText = offer.buttonText,
                    link = offer.link
                )
            }
            resultList.add(BaseItem.OffersItemUi(offerList))
        }
        resultList.add(BaseItem.HeaderUi(R.string.vacancy_for_you))
        val vacancyList = vacancyPayload.vacancyList
            .subList(0, 3)
            .map { vacancy ->
                BaseItem.VacancyUi(
                    id = vacancy.id,
                    lookingNumber = vacancy.lookingNumber,
                    isFavorite = vacancy.isFavorite,
                    title = vacancy.title,
                    town = vacancy.town,
                    company = vacancy.company,
                    previewText = vacancy.previewText,
                    publishedDate = DateUtil.convert(vacancy.publishedDate)
                )
            }
        resultList.addAll(vacancyList)
        resultList.add(BaseItem.ButtonOnClickItemUi(vacancyPayload.vacancyList.size))
        return resultList
    }
}