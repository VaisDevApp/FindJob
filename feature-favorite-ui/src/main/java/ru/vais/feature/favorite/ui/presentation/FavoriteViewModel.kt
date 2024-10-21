package ru.vais.feature.favorite.ui.presentation

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
import kotlinx.coroutines.withContext
import ru.vais.feature.favorite.ui.presentation.adapter.BaseItem
import ru.vais.feature.favorite.ui.presentation.mapper.FavoriteMapper
import ru.vais.feature.vacancy.data.domain.GetFavoriteVacancyUseCase
import ru.vais.feature.vacancy.data.domain.UpdateFavoriteVacancyUseCase
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
    private val updateFavoriteVacancyUseCase: UpdateFavoriteVacancyUseCase,
    private val getFavoriteVacancyUseCase: GetFavoriteVacancyUseCase
) : ViewModel() {

    private val _stateScreenFlow = MutableStateFlow<List<BaseItem>>(emptyList())
    val stateScreenFlow = _stateScreenFlow.asStateFlow()

    fun updateFavoriteStatus(vacancy: BaseItem.VacancyUi) {
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
            getFavoriteVacancyUseCase.getFavoriteVacancy()
                .map { FavoriteMapper.map(it) }
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.e(TAG, "Load data Error", it)
                    _stateScreenFlow.emit(emptyList())
                }
                .collect { _stateScreenFlow.emit(it) }
        }
    }

    companion object {
        private const val TAG = "FavoriteViewModel"
    }
}