package com.example.simpleweather.network.openweather.rawmodel

import com.google.gson.annotations.SerializedName

data class RawRain(
    @SerializedName("1h")
    val last_hour: Float? = null,
)