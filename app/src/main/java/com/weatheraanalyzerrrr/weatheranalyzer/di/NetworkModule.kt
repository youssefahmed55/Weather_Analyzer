package com.weatheraanalyzerrrr.weatheranalyzer.di


import com.weatheraanalyzerrrr.data.remote.main.MainApiService
import com.weatheraanalyzerrrr.data.remote.weekforecast.WeekForecastApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttp(): OkHttpClient{
      return OkHttpClient.Builder()
          .connectTimeout(20,TimeUnit.SECONDS)
          .readTimeout(20,TimeUnit.SECONDS)
          .retryOnConnectionFailure(false)
          .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
      return Retrofit.Builder()
          .baseUrl("https://api.openweathermap.org/data/2.5/")
          .client(okHttpClient)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
  }

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): MainApiService {
     return retrofit.create(MainApiService::class.java)
  }

    @Provides
    @Singleton
    fun provideWeekForecastApiService(retrofit: Retrofit): WeekForecastApiService {
        return retrofit.create(WeekForecastApiService::class.java)
    }
}