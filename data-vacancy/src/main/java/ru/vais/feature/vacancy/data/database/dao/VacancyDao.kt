package ru.vais.feature.vacancy.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.vais.feature.vacancy.data.database.entity.VacancyDb

@Dao
interface VacancyDao {
    @Query("SELECT*FROM Vacancy")
    fun getAllVacancy(): List<VacancyDb>

    @Query("SELECT*FROM Vacancy")
    fun getAllVacancyAsFlow(): Flow<List<VacancyDb>>

    @Query("SELECT*FROM Vacancy WHERE id = :id")
    fun getVacancyById(id: String): VacancyDb

    @Insert
    fun insert(vacancyDb: VacancyDb)

    @Query("UPDATE Vacancy SET isFavorite = :isFavorite WHERE id = :id")
    fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("DELETE FROM Vacancy")
    fun delete()

    @Transaction
    fun updateData(listVacancyDb: List<VacancyDb>) {
        delete()
        listVacancyDb.forEach { insert(it) }
    }

}