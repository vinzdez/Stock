package com.vince.myapplication.domain.repository

import com.vince.myapplication.domain.model.CompanyInfo
import com.vince.myapplication.domain.model.CompanyListing
import com.vince.myapplication.domain.model.IntradayInfo
import com.vince.myapplication.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>

}