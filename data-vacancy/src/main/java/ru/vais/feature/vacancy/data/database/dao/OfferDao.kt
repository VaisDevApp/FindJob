package ru.vais.feature.vacancy.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.vais.feature.vacancy.data.database.entity.OfferDb

@Dao
interface OfferDao {
    @Query("SELECT * FROM Offer")
    fun getAllOffer(): List<OfferDb>

    @Query("SELECT * FROM Offer")
    fun getAllOfferFlow(): Flow<List<OfferDb>>

    @Query("DELETE FROM Offer")
    fun delete()

    @Insert
    fun insert(offerDb: OfferDb)

    @Transaction
    fun updateData(listOfferDb: List<OfferDb>) {
        delete()
        listOfferDb.forEach { insert(it) }
    }
}