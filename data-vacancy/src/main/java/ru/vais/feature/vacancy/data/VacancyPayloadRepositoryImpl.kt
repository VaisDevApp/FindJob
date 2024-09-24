package ru.vais.feature.vacancy.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import ru.vais.feature.vacancy.data.database.AppDatabase
import ru.vais.feature.vacancy.data.database.entity.OfferDb
import ru.vais.feature.vacancy.data.database.entity.VacancyDb
import ru.vais.feature.vacancy.data.network.ServerApi
import ru.vais.feature.vacancy.data.network.entity.OffersApi
import ru.vais.feature.vacancy.data.network.entity.VacancyApi
import ru.vais.feature.vacancy.domain.VacancyPayloadRepository
import ru.vais.feature.vacancy.domain.entity.Offer
import ru.vais.feature.vacancy.domain.entity.Vacancy
import ru.vais.feature.vacancy.domain.entity.VacancyPayload
import javax.inject.Inject


class VacancyPayloadRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    private val dataBase: AppDatabase
) : VacancyPayloadRepository {

    override fun getVacancyPayload(): Flow<VacancyPayload> {
        return flow {
            SYNC_MUTEX.withLock {
                if (isNeedToLoadFromServer) {
                    syncData()
                    isNeedToLoadFromServer = false
                }
            }
            emit(Unit)
        }.flatMapLatest {
            getFlowVacancyPayloadFromDb()
        }
    }

    override fun getCountFavoriteVacancy(): Flow<Int> {
        return dataBase.getVacancyDbDao().getAllVacancyAsFlow()
            .map {
                it.filter { it.isFavorite }.size
            }
    }

    private fun getFlowVacancyPayloadFromDb(): Flow<VacancyPayload> {
        return combine(
            dataBase.getOfferDbDao().getAllOfferFlow(),
            dataBase.getVacancyDbDao().getAllVacancyAsFlow()
        ) { listOfferDb, listVacancyDb ->
            VacancyPayload(
                listVacancyDb.map { mapVacancyDbToVacancy(it) },
                listOfferDb.map { mapOfferBdToOffer(it) },
            )
        }
    }

    private suspend fun syncData() {
        val vacancyPayloadFromServer = serverApi.getVacancyPayLoad()
        val offerDbList = vacancyPayloadFromServer.offers.map {
            mapOfferApiToOfferBd(it)
        }
        val vacancyFromBd = dataBase.getVacancyDbDao().getAllVacancy()
        val vacancyDbList = vacancyPayloadFromServer.vacancies.map { vacancyApi ->
            val isFavoriteNewFromDb =
                vacancyFromBd.firstOrNull { vacancyApi.id == it.id }?.isFavorite
            if (isFavoriteNewFromDb != null) {
                vacancyApi.isFavorite = isFavoriteNewFromDb
            }
            mapVacancyApiToVacancyDb(vacancyApi)
        }
        withContext(Dispatchers.IO) {
            dataBase.getOfferDbDao().updateData(offerDbList)
            dataBase.getVacancyDbDao().updateData(vacancyDbList)
        }
    }

    private fun mapVacancyApiToVacancyDb(vacancyApi: VacancyApi): VacancyDb {
        return VacancyDb(
            id = vacancyApi.id,
            lookingNumber = vacancyApi.lookingNumber,
            isFavorite = vacancyApi.isFavorite,
            title = vacancyApi.title,
            town = vacancyApi.address?.town,
            company = vacancyApi.company,
            previewText = vacancyApi.experience?.previewText,
            publishedDate = vacancyApi.publishedDate
        )
    }

    private fun mapVacancyDbToVacancy(vacancyDb: VacancyDb): Vacancy {
        return Vacancy(
            id = vacancyDb.id,
            lookingNumber = vacancyDb.lookingNumber,
            isFavorite = vacancyDb.isFavorite,
            title = vacancyDb.title,
            town = vacancyDb.town,
            company = vacancyDb.company,
            previewText = vacancyDb.previewText,
            publishedDate = vacancyDb.publishedDate
        )
    }

    private fun mapOfferApiToOfferBd(offerApi: OffersApi): OfferDb {
        return OfferDb(
            id = 0,
            idFromServer = offerApi.id,
            title = offerApi.title,
            buttonText = offerApi.button?.text
        )
    }

    private fun mapOfferBdToOffer(offerBd: OfferDb): Offer {
        return Offer(
            id = offerBd.idFromServer,
            title = offerBd.title,
            buttonText = offerBd.buttonText
        )
    }

    override suspend fun updateFavoriteVacancy(id: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            dataBase.getVacancyDbDao().updateFavorite(id, isFavorite)
        }
    }

    companion object {
        private val SYNC_MUTEX = Mutex()
        private var isNeedToLoadFromServer: Boolean = true
    }
}