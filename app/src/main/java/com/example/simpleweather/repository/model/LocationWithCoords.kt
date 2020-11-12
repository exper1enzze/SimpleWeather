package com.example.simpleweather.repository.model

import com.example.simpleweather.local.model.LocationDB
import com.example.simpleweather.utils.diffutil.Identified

data class LocationWithCoords(
    val locationId: Int = -1,
    val fullAddress: String,
    val longitude: Float,
    val latitude: Float,
    val addressCity: String,
    val addressCounty: String?,
    val addressState: String?,
    val addressCountry: String?,

) : Identified
{
    override val identifier: Any = locationId

    fun toLocationDB(): LocationDB {
        return LocationDB(
            longitude,
            latitude,
            fullAddress,
            addressCity,
            addressCounty,
            addressState,
            addressCountry
        )
    }

}