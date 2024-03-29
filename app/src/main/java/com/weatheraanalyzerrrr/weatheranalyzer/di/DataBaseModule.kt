package com.weatheraanalyzerrrr.weatheranalyzer.di

import android.app.Application
import androidx.room.Room
import com.weatheraanalyzerrrr.data.local.AppDatabase
import com.weatheraanalyzerrrr.data.local.main.WeatherDao
import com.weatheraanalyzerrrr.data.local.weekforecast.WeekForecastDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    @Synchronized
    fun provideDatabase(application: Application?): AppDatabase {
        return Room.databaseBuilder(application!!, AppDatabase::class.java, "myDataBase")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    @Synchronized
    fun provideWeatherDao(myDatabase: AppDatabase): WeatherDao {
        return myDatabase.weatherDao()
    }

    @Provides
    @Singleton
    @Synchronized
    fun provideWeekForecastDaoDao(myDatabase: AppDatabase): WeekForecastDao {
        return myDatabase.weekForecastDao()
    }


}