package com.vince.myapplication.data.repository

import com.vince.myapplication.data.csv.CSVParser
import com.vince.myapplication.data.csv.IntradayInfoParser
import com.vince.myapplication.data.local.StockDatabase
import com.vince.myapplication.data.mapper.toCompanyInfo
import com.vince.myapplication.data.mapper.toCompanyListing
import com.vince.myapplication.data.mapper.toCompanyListingEntity
import com.vince.myapplication.data.remote.StockApi
import com.vince.myapplication.domain.model.CompanyInfo
import com.vince.myapplication.domain.model.CompanyListing
import com.vince.myapplication.domain.model.IntradayInfo
import com.vince.myapplication.domain.repository.StockRepository
import com.vince.myapplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(data = localListings.map { it.toCompanyListing() }))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(listings.map { it.toCompanyListingEntity() })
                emit(
                    Resource.Success(
                        data = dao.searchCompanyListing("").map { it.toCompanyListing() })
                )
                emit(Resource.Loading(false))
            }

        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load company info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load company info")
        }
    }

}