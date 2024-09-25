package ru.vais.feature.search.ui.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.vais.core.di.ViewModelKey
import ru.vais.feature.search.ui.presentation.full.SearchFullViewModel
import ru.vais.feature.search.ui.presentation.main.SearchViewModel

@Module
interface SearchModule {
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SearchFullViewModel::class)
    @Binds
    fun bindSearchFullViewModel(viewModel: SearchFullViewModel): ViewModel
}