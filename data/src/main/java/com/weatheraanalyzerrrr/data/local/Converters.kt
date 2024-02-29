package com.weatheraanalyzerrrr.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.Clouds
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.Coord
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.Main
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.Sys
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.Weather
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.Wind
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Daily
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Rain


class Converters {

    @TypeConverter
    fun fromCloudsToString(value: Clouds?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToClouds(s: String?): Clouds? {
        return Gson().fromJson<Clouds>(
            s, object : TypeToken<Clouds?>() {}.type
        )
    }

    @TypeConverter
    fun fromCoordToString(value: Coord?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToCoord(s: String?): Coord? {
        return Gson().fromJson<Coord>(
            s, object : TypeToken<Coord?>() {}.type
        )
    }

    @TypeConverter
    fun fromMainToString(value: Main?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToMain(s: String?): Main? {
        return Gson().fromJson<Main>(
            s, object : TypeToken<Main?>() {}.type
        )
    }

    @TypeConverter
    fun fromSysToString(value: Sys?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToSys(s: String?): Sys? {
        return Gson().fromJson<Sys>(
            s, object : TypeToken<Sys?>() {}.type
        )
    }

    @TypeConverter
    fun fromListWeatherToString(value: List<Weather>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToListWeather(s: String?): List<Weather>? {
        return Gson().fromJson<List<Weather>>(
            s, object : TypeToken<List<Weather>?>() {}.type
        )
    }

    @TypeConverter
    fun fromWindToString(value: Wind?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToWind(s: String?): Wind? {
        return Gson().fromJson<Wind>(
            s, object : TypeToken<Wind?>() {}.type
        )
    }

    @TypeConverter
    fun fromRainToString(value: Rain?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToRain(s: String?): Rain? {
        return Gson().fromJson<Rain>(
            s, object : TypeToken<Rain?>() {}.type
        )
    }

    @TypeConverter
    fun fromListDailyToString(value: List<Daily>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToListDaily(s: String?): List<Daily>? {
        return Gson().fromJson<List<Daily>>(
            s, object : TypeToken<List<Daily>?>() {}.type
        )
    }


}