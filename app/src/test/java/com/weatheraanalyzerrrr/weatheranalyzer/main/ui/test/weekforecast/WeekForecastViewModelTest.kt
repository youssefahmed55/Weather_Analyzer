package com.weatheraanalyzerrrr.weatheranalyzer.main.ui.test.weekforecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.weatheraanalyzerrrr.domain.usecase.weekforecast.GetDailyWeather
import com.weatheraanalyzerrrr.domain.usecase.weekforecast.GetDailyWeatherR
import com.weatheraanalyzerrrr.domain.usecase.weekforecast.InsertDailyWeatherR
import com.weatheraanalyzerrrr.weatheranalyzer.main.MainCoroutineRule
import com.weatheraanalyzerrrr.weatheranalyzer.main.repo.weekforecast.FakeWeekForecastRepo
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.ViewModelStates
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.weekforecast.WeekForecastViewModel
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
class WeekForecastViewModelTest {


    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeekForecastViewModel

    @Before
    fun setup() {
        viewModel = WeekForecastViewModel(
            GetDailyWeather(FakeWeekForecastRepo()),
            GetDailyWeatherR(FakeWeekForecastRepo()),
            InsertDailyWeatherR(FakeWeekForecastRepo()),
            SavedStateHandle()
        )
    }


    @Test
    fun testViewModel_Loading() = runTest {
        // Create an empty collector for the StateFlow
        testScope.launch {
            viewModel.dailyModel.collect()
        }


        // Can assert initial value
        assert(ViewModelStates.Loading == viewModel.dailyModel.value)
    }

    @Test
    fun testViewModel_success() = runTest {
        // Create an empty collector for the StateFlow

        testScope.launch {
            viewModel.dailyModel.collect()
        }

        viewModel.getDailyWeather(0.55, 0.33)
        assert(viewModel.dailyModel.value is ViewModelStates.Success)

    }


}