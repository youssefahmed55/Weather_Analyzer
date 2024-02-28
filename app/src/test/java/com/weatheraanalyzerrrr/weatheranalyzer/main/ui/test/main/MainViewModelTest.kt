package com.weatheraanalyzerrrr.weatheranalyzer.main.ui.test.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weatheraanalyzerrrr.domain.usecase.main.DeleteHourlyWeatherDataBaseR
import com.weatheraanalyzerrrr.domain.usecase.main.GetCurrentCityNameAndWeather
import com.weatheraanalyzerrrr.domain.usecase.main.GetCurrentCityNameAndWeatherR
import com.weatheraanalyzerrrr.domain.usecase.main.GetCurrentWeatherByName
import com.weatheraanalyzerrrr.domain.usecase.main.GetHourlyWeather
import com.weatheraanalyzerrrr.domain.usecase.main.GetHourlyWeatherR
import com.weatheraanalyzerrrr.domain.usecase.main.GetTheCurrentLocation
import com.weatheraanalyzerrrr.domain.usecase.main.InsertCurrentWeatherR
import com.weatheraanalyzerrrr.domain.usecase.main.InsertHourlyWeatherR
import com.weatheraanalyzerrrr.weatheranalyzer.main.MainCoroutineRule
import com.weatheraanalyzerrrr.weatheranalyzer.main.repo.main.FakeWeatherRepo
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.ViewModelStates
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainViewModelTest {


    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(
            GetCurrentCityNameAndWeather(FakeWeatherRepo()),
            GetHourlyWeather(FakeWeatherRepo()),
            GetCurrentWeatherByName(FakeWeatherRepo()),
            GetCurrentCityNameAndWeatherR(FakeWeatherRepo()),
            GetHourlyWeatherR(FakeWeatherRepo()),
            InsertCurrentWeatherR(FakeWeatherRepo()),
            InsertHourlyWeatherR(FakeWeatherRepo()),
            DeleteHourlyWeatherDataBaseR(FakeWeatherRepo()),
            GetTheCurrentLocation(FakeWeatherRepo())
        )
    }
    @Test
    fun testViewModel_Loading() = runTest {
      // Create an empty collector for the StateFlow
        testScope.launch {

            viewModel.currentWeatherModel.collect()
            viewModel.hourlyModel.collect()

        }


        // Can assert initial value
        assert(ViewModelStates.Loading == viewModel.currentWeatherModel.value)
        assert(ViewModelStates.Loading == viewModel.hourlyModel.value)
    }
    @Test
    fun testViewModel_success() = runTest {
      // Create an empty collector for the StateFlow

        testScope.launch {
            viewModel.currentWeatherModel.collect()
            viewModel.hourlyModel.collect()
        }


        viewModel.getCurrentWeather(-0.1257, 51.5085)
        assert(viewModel.currentWeatherModel.value is ViewModelStates.Success)


        viewModel.getHourly(-0.1257, 51.5085)
        assert(viewModel.hourlyModel.value is ViewModelStates.Success)


        viewModel.getCurrentWeatherByN("Cairo")
        assert(viewModel.currentWeatherModel.value is ViewModelStates.Success && viewModel.hourlyModel.value is ViewModelStates.Success)
    }


}