package ru.vais.feature.favorite.ui.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.vais.core.di.ViewModelKey
import ru.vais.feature.favorite.ui.FavoriteViewModel

@Module
interface FavoriteModule {
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    @Binds
    fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel
}