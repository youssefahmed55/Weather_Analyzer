package com.weatheraanalyzerrrr

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.weatheraanalyzerrrr.data.local.AppDatabase
import com.weatheraanalyzerrrr.data.local.main.WeatherDao
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    @JvmField
    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: WeatherDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.weatherDao()
    }

    @After
    fun teardown(){
        database.close()
    }


    @Test
    fun insertCurrentWeather() = runBlocking {
        val currentWeather = CurrentModelResponse(name = "Cairo")
        dao.insertCurrentWeather(currentWeather)
        val currentWeatherFromDataBase = dao.getCurrentWeather()
       assert(currentWeatherFromDataBase.name.equals(currentWeather.name))
    }

    @Test
    fun insertHourlyWeatherList() = runBlocking {
        val hourlyWeatherList = listOf(Hourly(temp = 24.0),Hourly(temp = 20.0),Hourly(temp = 10.0))
        dao.insertHourlyWeather(hourlyWeatherList)
        val hourlyWeatherListFromDataBase = dao.getHourlyWeather()
        assert(hourlyWeatherListFromDataBase.size == hourlyWeatherList.size)
        assert(hourlyWeatherListFromDataBase[0].temp == 24.0)
        assert(hourlyWeatherListFromDataBase[1].temp == 20.0)
        assert(hourlyWeatherListFromDataBase[2].temp == 10.0)
    }





}