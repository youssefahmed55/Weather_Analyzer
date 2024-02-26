package com.weatheraanalyzerrrr.data.local.weekforecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import retrofit2.http.Body

@Dao
interface WeekForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWeather(@Body dailyModelResponse: DailyModelResponse)

    @Query("SELECT * FROM DailyTable")
    suspend fun getDailyWeather(): DailyModelResponse

}