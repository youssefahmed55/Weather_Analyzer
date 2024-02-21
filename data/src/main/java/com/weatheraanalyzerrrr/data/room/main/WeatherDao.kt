package com.weatheraanalyzerrrr.data.room.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly
import retrofit2.http.Body

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(@Body currentModelResponse: CurrentModelResponse)

    @Query("SELECT * FROM CurrentWeatherTable")
    suspend fun getCurrentWeather(): CurrentModelResponse


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(@Body list: List<Hourly>)

    @Query("SELECT * FROM HourlyTable")
    suspend fun getHourlyWeather(): List<Hourly>

    @Query("DELETE FROM HourlyTable")
    suspend fun deleteAllHourlyWeather()
}