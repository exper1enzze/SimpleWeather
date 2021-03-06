package com.example.simpleweather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simpleweather.data.local.model.DailyConditionDB
import com.example.simpleweather.data.local.model.HourlyConditionDB
import com.example.simpleweather.data.local.model.LocationDB
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(entity = LocationDB::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNewLocation(location: LocationDB): Long

    @Query("UPDATE locations SET refreshTimeDaily = :lastUpdateTime WHERE locationId = :locationId")
    suspend fun updateDailyRefreshTime(lastUpdateTime: Long, locationId: Long)

    @Query("UPDATE locations SET refreshTimeHourly = :lastUpdateTime WHERE locationId = :locationId")
    suspend fun updateHourlyRefreshTime(lastUpdateTime: Long, locationId: Long)

    @Query("UPDATE locations SET refreshTimeCurrent = :lastUpdateTime WHERE locationId = :locationId")
    suspend fun updateCurrentRefreshTime(lastUpdateTime: Long, locationId: Long)

    @Query("SELECT * FROM locations")
    fun getSavedLocations(): Flow<List<LocationDB>>

    @Query("SELECT * FROM locations")
    fun getSavedLocationsSync(): List<LocationDB>

    @Query("SELECT * FROM locations WHERE locationId = :locationId")
    suspend fun getSavedLocationById(locationId: Long): LocationDB

    @Query("DELETE FROM locations WHERE locationId = :locationId")
    suspend fun deleteLocation(locationId: Long): Int



    @Insert(entity = DailyConditionDB::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDailyForecast(dailyForecastList: List<DailyConditionDB>)

    @Query("SELECT * FROM daily_weather_conditions WHERE locationParentId = :locationId AND timeStamp >= :currentTimeStamp")
    fun getDailyForecast(locationId: Long, currentTimeStamp: Long): Flow<List<DailyConditionDB>>



    @Insert(entity = HourlyConditionDB::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHourlyForecast(hourlyForecast: List<HourlyConditionDB>)

    @Query("SELECT * FROM hourly_weather_conditions WHERE locationParentId = :locationId AND timeStamp >= :currentTimeStamp")
    fun getHourlyForecast(locationId: Long, currentTimeStamp: Long): Flow<List<HourlyConditionDB>>



    @Query("SELECT * FROM hourly_weather_conditions WHERE locationParentId = :locationId AND timeStamp = :currentTimeStamp")
    fun getCurrentForecast(locationId: Long, currentTimeStamp: Long) : Flow<HourlyConditionDB>


}