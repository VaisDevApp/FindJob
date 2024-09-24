package ru.vais.findwork.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.vais.core.di.ViewModelKey
import ru.vais.findwork.MainViewModel

@Module
interface AppModule {
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}