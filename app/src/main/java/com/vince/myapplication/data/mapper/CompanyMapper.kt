package com.vince.myapplication.data.mapper

import com.vince.myapplication.data.local.CompanyListingEntity
import com.vince.myapplication.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(name, symbol, exchange)
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(name, symbol, exchange)
}