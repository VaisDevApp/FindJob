package ru.vais.findwork.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.vais.core.di.BaseComponent
import ru.vais.feature.favorite.ui.di.FavoriteModule
import ru.vais.feature.search.ui.di.SearchModule
import ru.vais.feature.sharit.detail.ui.vacancydetailfeatute.presentation.di.VacancyDetailModule
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, SearchModule::class, FavoriteModule::class, AppModule::class, VacancyDetailModule::class])
interface AppComponent : BaseComponent {

    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}