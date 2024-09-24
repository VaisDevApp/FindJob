package ru.vais.findwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.vais.feature.vacancy.domain.GetCountFavoriteVacancyUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getCountFavoriteVacancyUseCase: GetCountFavoriteVacancyUseCase
) : ViewModel() {
    val screenStateFlow = MutableStateFlow<Int>(0)

    fun loadData (){
        viewModelScope.launch {
            getCountFavoriteVacancyUseCase.getCountFavoriteVacancy().flowOn(Dispatchers.IO).collect{
                screenStateFlow.emit(it)
            }
        }
    }
}