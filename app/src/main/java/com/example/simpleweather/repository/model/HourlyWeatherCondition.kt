package com.example.simpleweather.repository.model

import com.example.simpleweather.local.model.HourlyWeatherConditionDB
import com.example.simpleweather.utils.diffutil.Identified

data class HourlyWeatherCondition(
    val timeStamp: Int,

    val temp: Float?,
    val tempFeelsLike: Float?,
    val pressure: Int?,
    val humidity: Int?,
    val windSpeed: Float?,
    val windDeg: Int?,

    val weatherId: Int?,
    val weatherName: String?,
    val weatherDescription: String?,
    val weatherIcon: String?,

    val probabilityOfPrecipitation: Float?,
    val snowVolume: Float?,
    val rainVolume: Float?
) : Identified
{
    override val identifier: Any = timeStamp

    fun toHourlyWeatherConditionDB(locationId: Long): HourlyWeatherConditionDB {
        return HourlyWeatherConditionDB(
            locationId.toInt(),
            timeStamp,
            temp,
            tempFeelsLike,
            pressure,
            humidity,
            windSpeed,
            windDeg,
            weatherId,
            weatherName,
            weatherDescription,
            weatherIcon,
            probabilityOfPrecipitation,
            snowVolume,
            rainVolume
        )
    }
}