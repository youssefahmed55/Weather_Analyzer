package com.weatheraanalyzerrrr.weatheranalyzer.di

import android.app.Application
import androidx.room.Room
import com.example.data.room.AppDatabase
import com.weatheraanalyzerrrr.data.room.main.WeatherDao
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
    fun provideMealsCategoryDao(myDatabase: AppDatabase): WeatherDao {
        return myDatabase.weatherDao()
    }


}