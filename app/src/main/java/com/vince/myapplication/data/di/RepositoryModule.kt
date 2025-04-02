package com.vince.myapplication.data.di

import com.vince.myapplication.data.csv.CSVParser
import com.vince.myapplication.data.csv.CompanyListingParser
import com.vince.myapplication.data.csv.IntradayInfoParser
import com.vince.myapplication.data.repository.StockRepositoryImpl
import com.vince.myapplication.domain.model.CompanyListing
import com.vince.myapplication.domain.model.IntradayInfo
import com.vince.myapplication.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository

}