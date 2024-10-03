package ru.vais.feature.search.ui.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vais.feature.search.ui.presentation.adapter.search.BaseItem
import ru.vais.feature.search.ui.presentation.main.mapper.SearchMapper
import ru.vais.feature.vacancy.data.network.domain.GetAllVacancyPayloadUseCase
import ru.vais.feature.vacancy.data.network.domain.UpdateFavoriteVacancyUseCase
import javax.inject.Inject


class SearchViewModel @Inject constructor(
    private val getAllVacancyPayloadUseCase: GetAllVacancyPayloadUseCase,
    private val updateFavoriteVacancyUseCase: UpdateFavoriteVacancyUseCase
) : ViewModel() {

    private val _stateScreenFlow = MutableStateFlow<StateScreen>(StateScreen.Progress)
    val stateScreenFlow = _stateScreenFlow.asStateFlow()

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
            _stateScreenFlow.emit(StateScreen.Progress)
            getAllVacancyPayloadUseCase.getAllVacancyPayload()
                .map { vacancyPayload -> SearchMapper.map(vacancyPayload) }
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.e(TAG, "Load data with error", it )
                    _stateScreenFlow.emit(StateScreen.Error)
                }
                .collect {
                    _stateScreenFlow.emit(StateScreen.Content(it))
                }
        }
    }

    sealed class StateScreen {
        data object Error : StateScreen()
        data object Progress : StateScreen()
        class Content(val baseItemList: List<BaseItem>) : StateScreen()
    }
    companion object{
        private const val TAG = "SearchViewModel"
    }
}