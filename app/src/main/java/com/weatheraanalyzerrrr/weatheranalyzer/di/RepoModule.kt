package com.weatheraanalyzerrrr.weatheranalyzer.di


import com.weatheraanalyzerrrr.data.remote.main.ApiService
import com.weatheraanalyzerrrr.data.repo.main.WeatherRepoImpl
import com.weatheraanalyzerrrr.data.room.main.WeatherDao
import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
   @Provides
   fun provideRepo(apiService: ApiService, weatherDao: WeatherDao): WeatherRepo{
       return WeatherRepoImpl(apiService,weatherDao)
   }
}