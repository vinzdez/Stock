package com.vince.myapplication.data.mapper

import com.vince.myapplication.data.local.CompanyListingEntity
import com.vince.myapplication.data.remote.dto.CompanyInfoDto
import com.vince.myapplication.domain.model.CompanyInfo
import com.vince.myapplication.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(name, symbol, exchange)
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(name, symbol, exchange)
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}