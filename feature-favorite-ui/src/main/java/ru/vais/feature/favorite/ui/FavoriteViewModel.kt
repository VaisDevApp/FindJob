package ru.vais.feature.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vais.feature.vacancy.domain.GetFavoriteVacancyUseCase
import ru.vais.feature.vacancy.domain.UpdateFavoriteVacancyUseCase
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
    private val updateFavoriteVacancyUseCase: UpdateFavoriteVacancyUseCase,
    private val getFavoriteVacancyUseCase: GetFavoriteVacancyUseCase
): ViewModel() {

    val flow = MutableStateFlow<List<BaseFavoriteItem>>(emptyList())

    fun update(vacancy: BaseFavoriteItem.VacancyUi) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateFavoriteVacancyUseCase.updateFavoriteVacancy(
                    vacancy.id,
                    vacancy.isFavorite.not()
                )
            }
        }
    }

    fun loadDate() {
        viewModelScope.launch {
            val vacancyUiList = getFavoriteVacancyUseCase.getFavoriteVacancy()
            vacancyUiList
                .catch {
                    //toDo обработать исключение
                }
                .collect {
                    val headerUi = BaseFavoriteItem.HeaderUi(it.size)
                    val listForFlow = mutableListOf<BaseFavoriteItem>()
                    listForFlow.add(headerUi)
                    val vacancyList = it.map {
                        BaseFavoriteItem.VacancyUi(
                            it.id,
                            it.lookingNumber,
                            it.isFavorite,
                            it.title,
                            it.town,
                            it.company,
                            it.previewText,
                            it.publishedDate
                        )
                    }
                    listForFlow.addAll(vacancyList)
                    flow.emit(listForFlow)
                }
        }
    }
}