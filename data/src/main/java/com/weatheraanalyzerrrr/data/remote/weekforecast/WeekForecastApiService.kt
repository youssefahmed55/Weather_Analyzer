package com.weatheraanalyzerrrr.data.remote.weekforecast

import com.weatheraanalyzerrrr.data.BuildConfig
import com.weatheraanalyzerrrr.data.Constants.excludeDays
import com.weatheraanalyzerrrr.data.Constants.units
import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeekForecastApiService {

    //Get Weather Degree Of Next Week
    @GET("onecall?units=$units&appid=${BuildConfig.API_KEY}&exclude=$excludeDays")
    suspend fun getWeatherOfSevenDaysRequest(@Query("lat") lat : Double, @Query("lon") lon : Double, @Query("lang") lang :String) : DailyModelResponse

}