package com.weatheraanalyzerrrr

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.weatheraanalyzerrrr.data.local.AppDatabase
import com.weatheraanalyzerrrr.data.local.main.WeatherDao
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class WeatherDaoTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase

    @Inject
    @Named("test_weatherDao")
    lateinit var dao: WeatherDao

    @Before
    fun setup() {
        hiltRule.inject()
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