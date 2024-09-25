package ru.vais.feature.search.ui.presentation.full

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vais.feature.search.ui.presentation.adapter.BaseItem
import ru.vais.feature.search.ui.presentation.full.mapper.SearchFullMapper
import ru.vais.feature.vacancy.domain.GetAllVacancyPayloadUseCase
import ru.vais.feature.vacancy.domain.UpdateFavoriteVacancyUseCase
import javax.inject.Inject

class SearchFullViewModel @Inject constructor(
    private val getAllVacancyPayloadUseCase: GetAllVacancyPayloadUseCase,
    private val updateFavoriteVacancyUseCase: UpdateFavoriteVacancyUseCase
) : ViewModel() {

    private val _flowStateScreen = MutableStateFlow<StateScreen>(StateScreen.Progress)
    val flowStateScreen = _flowStateScreen.asStateFlow()

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
            _flowStateScreen.emit(StateScreen.Progress)
            getAllVacancyPayloadUseCase.getAllVacancyPayload()
                .map { vacancyPayload -> SearchFullMapper.map(vacancyPayload) }
                .flowOn(Dispatchers.IO)
                .catch {
                    _flowStateScreen.emit(StateScreen.Error)
                }
                .collect {
                    _flowStateScreen.emit(StateScreen.Content(it))
                }
        }
    }

    sealed class StateScreen {
        data object Error : StateScreen()
        data object Progress : StateScreen()
        class Content(val baseItemList: List<BaseItem>) : StateScreen()
    }
}