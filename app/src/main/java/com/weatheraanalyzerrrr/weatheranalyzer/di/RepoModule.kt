package com.weatheraanalyzerrrr.weatheranalyzer.di


import com.weatheraanalyzerrrr.data.remote.main.MainApiService
import com.weatheraanalyzerrrr.data.remote.weekforecast.WeekForecastApiService
import com.weatheraanalyzerrrr.data.repo.main.WeatherRepoImpl
import com.weatheraanalyzerrrr.data.repo.weekforecast.WeekForecastRepoImpl
import com.weatheraanalyzerrrr.data.room.main.WeatherDao
import com.weatheraanalyzerrrr.data.room.weekforecast.WeekForecastDao
import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo
import com.weatheraanalyzerrrr.domain.repo.weekforecast.WeekForecastRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
   @Provides
   fun provideWeatherRepo(mainApiService: MainApiService, weatherDao: WeatherDao): WeatherRepo{
       return WeatherRepoImpl(mainApiService,weatherDao)
   }

    @Provides
    fun provideWeekForecastRepo( weekForecastApiService: WeekForecastApiService, weekForecastDao: WeekForecastDao): WeekForecastRepo{
        return WeekForecastRepoImpl(weekForecastApiService,weekForecastDao)
    }
}