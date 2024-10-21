package ru.vais.feature.sharit.detail.ui.vacancydetailfeatute.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vais.feature.core.ui.DateUtil
import ru.vais.feature.vacancy.data.database.DataBaseFactory
import ru.vais.feature.vacancy.data.domain.GetVacancyByIdUseCase
import ru.vais.feature.vacancy.data.domain.UpdateFavoriteVacancyUseCase
import ru.vais.feature.vacancy.data.domain.entity.Vacancy
import javax.inject.Inject

class VacancyDetailViewModel @Inject constructor(
    private val getVacancyByIdUseCase: GetVacancyByIdUseCase,
    private val updateFavoriteVacancyUseCase: UpdateFavoriteVacancyUseCase
) : ViewModel() {

    private val _stateScreenFlow = MutableStateFlow<StateScreen>(StateScreen.Progress)
    val stateScreenFlow = _stateScreenFlow.asStateFlow()
    fun loadVacancy(id: String) {

        viewModelScope.launch {
            _stateScreenFlow.emit(StateScreen.Progress)
            withContext(Dispatchers.IO){
                try {
                    val vacancyDb = getVacancyByIdUseCase.getVacancyById(id)
                    val vacancyDetailUi = VacancyDetailUi(
                        id = vacancyDb.id,
                        lookingNumber = vacancyDb.lookingNumber,
                        isFavorite = vacancyDb.isFavorite,
                        title = vacancyDb.title,
                        town = vacancyDb.town,
                        company = vacancyDb.company,
                        previewText = vacancyDb.previewText,
                        publishedDate = DateUtil.convert(vacancyDb.publishedDate),
                        salary = vacancyDb.salary,
                        schedules = vacancyDb.schedules,
                        appliedNumber = vacancyDb.appliedNumber,
                        description = vacancyDb.description,
                        responsibilities = vacancyDb.responsibilities,
                        questions = vacancyDb.questions
                    )
                    _stateScreenFlow.emit(StateScreen.Content(vacancyDetailUi))
                }catch (e:Exception){
                    _stateScreenFlow.emit(StateScreen.Error)
                    e.printStackTrace()
                }
            }
        }

    }

    fun updateFavoriteStatus(vacancy: VacancyDetailUi) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateFavoriteVacancyUseCase.updateFavoriteVacancy(
                    vacancy.id,
                    vacancy.isFavorite.not()
                )
                loadVacancy(vacancy.id)
            }
        }

    }


    sealed class StateScreen {
        class Content(val content: VacancyDetailUi) : StateScreen()
        data object Progress : StateScreen()
        data object Error : StateScreen()
    }
}