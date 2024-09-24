package ru.vais.feature.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.vais.feature.vacancy.domain.GetAllVacancyPayloadUseCase
import ru.vais.feature.vacancy.domain.UpdateFavoriteVacancyUseCase
import javax.inject.Inject


class SearchViewModel @Inject constructor(
    private val getAllVacancyPayloadUseCase: GetAllVacancyPayloadUseCase,
    private val updateFavoriteVacancyUseCase: UpdateFavoriteVacancyUseCase
) : ViewModel() {

    val flowStateScreen = MutableStateFlow<StateScreen>(StateScreen.ProgressBar())

    private var needAllVacancy = true

    fun getAllVacancyInFragment() {
        needAllVacancy = false
        loadData()
    }

    fun changeFavorite(vacancyUi: BaseItem.VacancyUi) {
        viewModelScope.launch {
            updateFavoriteVacancyUseCase.updateFavoriteVacancy(
                vacancyUi.id,
                vacancyUi.isFavorite.not()
            )
        }
    }

    fun loadData() {
        viewModelScope.launch {
            flowStateScreen.emit(StateScreen.ProgressBar())
            getAllVacancyPayloadUseCase.getAllVacancyPayload()
                .flowOn(Dispatchers.IO)
                .catch {
                    flowStateScreen.emit(StateScreen.Error())
                }
                .collect {
                    val resultList = mutableListOf<BaseItem>()
                    resultList.add(BaseItem.FindItemUi())
                    val offerList = it.offerList.map {
                        OfferItem(
                            id = it.id,
                            title = it.title,
                            buttonText = it.buttonText
                        )
                    }
                    resultList.add(BaseItem.OffersItemUi(offerList))
                    resultList.add(BaseItem.HeaderUi(R.string.vacancy_for_you))

                    val vacancyList = it.vacancyList.map {
                        BaseItem.VacancyUi(
                            id = it.id,
                            lookingNumber = it.lookingNumber,
                            isFavorite = it.isFavorite,
                            title = it.title,
                            town = it.town,
                            company = it.company,
                            previewText = it.previewText,
                            publishedDate = it.publishedDate
                        )
                    }
                    if (needAllVacancy) {
                        for (i in 0..2) {
                            resultList.add(vacancyList[i])
                        }
                    } else {
                        resultList.addAll(vacancyList)
                    }
                    if (needAllVacancy) {
                        resultList.add(BaseItem.ButtonOnClickItemUi(vacancyList.size))
                    }
                    flowStateScreen.emit(StateScreen.Content(resultList))
                }
        }
    }

    sealed class StateScreen {
        class Error : StateScreen()

        class ProgressBar : StateScreen()

        class Content(
            val baseItemList: List<BaseItem>
        ) : StateScreen()

    }
}