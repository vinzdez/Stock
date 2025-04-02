package com.vince.myapplication.data.remote

import com.vince.myapplication.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET(value = "query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    @GET(value = "query?function=TIME_SERIES&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    @GET(value = "query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): CompanyInfoDto

    companion object {
        const val API_KEY = "RAGJR3IMPUKS4UYC"
        const val BASE_URL = "https://alphavantage.co"

    }

}