package com.weatheraanalyzerrrr.weatheranalyzer.di


import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo
import com.weatheraanalyzerrrr.domain.repo.weekforecast.WeekForecastRepo
import com.weatheraanalyzerrrr.domain.usecase.main.DeleteHourlyWeatherDataBaseR
import com.weatheraanalyzerrrr.domain.usecase.main.GetCurrentWeatherByName
import com.weatheraanalyzerrrr.domain.usecase.main.GetCurrentCityNameAndWeather
import com.weatheraanalyzerrrr.domain.usecase.main.GetCurrentCityNameAndWeatherR
import com.weatheraanalyzerrrr.domain.usecase.main.GetHourlyWeather
import com.weatheraanalyzerrrr.domain.usecase.main.GetHourlyWeatherR
import com.weatheraanalyzerrrr.domain.usecase.main.InsertCurrentWeatherR
import com.weatheraanalyzerrrr.domain.usecase.main.InsertHourlyWeatherR
import com.weatheraanalyzerrrr.domain.usecase.weekforecast.GetDailyWeather
import com.weatheraanalyzerrrr.domain.usecase.weekforecast.GetDailyWeatherR
import com.weatheraanalyzerrrr.domain.usecase.weekforecast.InsertDailyWeatherR
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCurrentWeatherUseCase(weatherRepo: WeatherRepo): GetCurrentCityNameAndWeather {
        return GetCurrentCityNameAndWeather(weatherRepo)
    }

    @Provides
    fun provideGetHourlyWeatherUseCase(weatherRepo: WeatherRepo): GetHourlyWeather {
        return GetHourlyWeather(weatherRepo)
    }

    @Provides
    fun provideGetCurrentWeatherByNameUseCase(weatherRepo: WeatherRepo): GetCurrentWeatherByName {
        return GetCurrentWeatherByName(weatherRepo)
    }

    @Provides
    fun provideGetCurrentCityNameAndWeatherRUseCase(weatherRepo: WeatherRepo): GetCurrentCityNameAndWeatherR {
        return GetCurrentCityNameAndWeatherR(weatherRepo)
    }

    @Provides
    fun provideGetHourlyWeatherRUseCase(weatherRepo: WeatherRepo): GetHourlyWeatherR {
        return GetHourlyWeatherR(weatherRepo)
    }

    @Provides
    fun provideInsertCurrentWeatherRUseCase(weatherRepo: WeatherRepo): InsertCurrentWeatherR {
        return InsertCurrentWeatherR(weatherRepo)
    }

    @Provides
    fun provideInsertHourlyWeatherRUseCase(weatherRepo: WeatherRepo): InsertHourlyWeatherR {
        return InsertHourlyWeatherR(weatherRepo)
    }

    @Provides
    fun provideDeleteHourlyWeatherDataBaseRUseCase(weatherRepo: WeatherRepo): DeleteHourlyWeatherDataBaseR {
        return DeleteHourlyWeatherDataBaseR(weatherRepo)
    }

    @Provides
    fun provideGetDailyWeatherUseCase(weekForecastRepo: WeekForecastRepo): GetDailyWeather {
        return GetDailyWeather(weekForecastRepo)
    }

    @Provides
    fun provideGetDailyWeatherRUseCase(weekForecastRepo: WeekForecastRepo): GetDailyWeatherR {
        return GetDailyWeatherR(weekForecastRepo)
    }

    @Provides
    fun provideInsertDailyWeatherRUseCase(weekForecastRepo: WeekForecastRepo): InsertDailyWeatherR {
        return InsertDailyWeatherR(weekForecastRepo)
    }


}