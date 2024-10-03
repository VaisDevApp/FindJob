package ru.vais.feature.sharit.detail.ui.vacancydetailfeatute.presentation.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.vais.core.di.ViewModelKey
import ru.vais.feature.sharit.detail.ui.vacancydetailfeatute.presentation.VacancyDetailViewModel

@Module
interface VacancyDetailModule {

    @IntoMap
    @ViewModelKey(VacancyDetailViewModel::class)
    @Binds
    fun bindVacancyDetailViewModel(viewModel: VacancyDetailViewModel): ViewModel
}