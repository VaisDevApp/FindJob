package ru.vais.feature.vacancy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vais.feature.vacancy.data.database.dao.OfferDao
import ru.vais.feature.vacancy.data.database.dao.VacancyDao
import ru.vais.feature.vacancy.data.database.entity.OfferDb
import ru.vais.feature.vacancy.data.database.entity.VacancyDb

@Database(entities = [VacancyDb::class, OfferDb::class], version = 3)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun getVacancyDbDao(): VacancyDao

    abstract fun getOfferDbDao(): OfferDao
}