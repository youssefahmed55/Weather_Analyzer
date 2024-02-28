package com.weatheraanalyzerrrr.weatheranalyzer.di

import android.content.Context
import androidx.room.Room
import com.weatheraanalyzerrrr.data.local.AppDatabase
import com.weatheraanalyzerrrr.data.local.main.WeatherDao
import com.weatheraanalyzerrrr.data.local.weekforecast.WeekForecastDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseTestModule {

    @Provides
    @Singleton
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()


    @Provides
    @Singleton
    @Synchronized
    @Named("test_weatherDao")
    fun provideWeatherDao(myDatabase: AppDatabase): WeatherDao {
        return myDatabase.weatherDao()
    }

    @Provides
    @Singleton
    @Synchronized
    @Named("test_weekForecastDao")
    fun provideWeekForecastDaoDao(myDatabase: AppDatabase): WeekForecastDao {
        return myDatabase.weekForecastDao()
    }

}