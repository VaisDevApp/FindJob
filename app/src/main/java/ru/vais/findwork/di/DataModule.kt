package ru.vais.findwork.di

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.vais.core.di.ViewModelKey
import ru.vais.feature.search.ui.SearchViewModel
import ru.vais.feature.vacancy.data.VacancyPayloadRepositoryImpl
import ru.vais.feature.vacancy.data.database.AppDatabase
import ru.vais.feature.vacancy.data.database.DataBaseFactory
import ru.vais.feature.vacancy.data.network.RetrofitApiFactory
import ru.vais.feature.vacancy.data.network.ServerApi
import ru.vais.feature.vacancy.domain.VacancyPayloadRepository
import ru.vais.findwork.MainViewModel
import javax.inject.Singleton

@Module
interface DataModule {
    @Binds
    fun bindVacancyAndOfferRepository(impl: VacancyPayloadRepositoryImpl): VacancyPayloadRepository

    companion object {
        @Singleton
        @Provides
        fun provideApi(): ServerApi {
            return RetrofitApiFactory.create()
        }

        @Singleton
        @Provides
        fun provideDataBase(application: Application): AppDatabase {
            return DataBaseFactory.create(application)
        }
    }
}