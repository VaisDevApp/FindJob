package ru.vais.feature.vacancy.data.database

import android.content.Context
import androidx.room.Room

object DataBaseFactory {
    fun create(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "dataBaseVacancy")
            .fallbackToDestructiveMigration()
            .build()
    }
}