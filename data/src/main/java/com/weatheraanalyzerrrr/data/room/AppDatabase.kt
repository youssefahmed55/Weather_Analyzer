package com.weatheraanalyzerrrr.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weatheraanalyzerrrr.data.room.main.WeatherDao
import com.weatheraanalyzerrrr.data.room.weekforecast.WeekForecastDao
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Daily
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly


@Database(
    entities = [CurrentModelResponse::class, Hourly::class,DailyModelResponse::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun weekForecastDao() : WeekForecastDao
}