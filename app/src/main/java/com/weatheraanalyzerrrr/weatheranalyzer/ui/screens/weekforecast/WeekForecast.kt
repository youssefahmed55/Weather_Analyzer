package com.weatheraanalyzerrrr.weatheranalyzer.ui.screens.weekforecast

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Daily
import com.weatheraanalyzerrrr.weatheranalyzer.R
import com.weatheraanalyzerrrr.weatheranalyzer.ui.screens.ViewModelStates
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

private const val TAG = "WeekForecast"

@Preview(showBackground = true, showSystemUi = true, device = "id:Nexus One")
@Composable
fun WeekForecast(
    navController: NavController? = null,
    viewModel: WeekForecastViewModel = hiltViewModel()
) {

    //Initialize cityNameState
    val cityNameState by viewModel.cityNameState.collectAsState()
    //Initialize dailyState
    val dailyState by viewModel.dailyModel.collectAsState()
    //Initialize dailyModels
    val dailyModels = remember { mutableStateOf(emptyList<Daily>()) }
    //Initialize snackBarHostState
    val snackBarHostState = remember { SnackbarHostState() }

    val backState = remember { mutableStateOf(false) }


    ObserverDailyWeatherData(dailyState, dailyModels, snackBarHostState)
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(top = 20.sdp, start = 10.sdp, end = 10.sdp),
        snackbarHost = { SnackbarHost(snackBarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            if (!backState.value) {
                                navController?.popBackStack()
                                backState.value = true
                            }
                                   },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = cityNameState,
                    fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
                    fontSize = 15.ssp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .testTag("cityNameWeekForecast")
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.size(30.sdp))
            Text(
                text = stringResource(R.string.next_week),
                fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
                fontSize = 15.ssp,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.size(20.sdp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(dailyModels.value) {
                    WeekForecastItem(it, Modifier.padding(bottom = 5.sdp))
                }
            }

        }

    }


}

@Composable
fun ObserverDailyWeatherData(
    dailyState: ViewModelStates<DailyModelResponse>,
    dailyModels: MutableState<List<Daily>>,
    snackBarHostState: SnackbarHostState
) {
    LaunchedEffect(dailyState) {
        when (dailyState) {
            is ViewModelStates.Success -> {
                val data = dailyState.data.daily
                data?.let { dailyModels.value = it }
            }

            is ViewModelStates.Error -> {
                Log.d(TAG, "Error Hourly")
                val data = dailyState.data?.daily    //Get Room DataBase

                data?.let { dailyModels.value = it }

                snackBarHostState
                    .showSnackbar(
                        message = dailyState.message,
                        actionLabel = "Skip",
                        // Defaults to SnackBarDuration.Short
                        duration = SnackbarDuration.Short
                    )
            }

            else -> {}
        }
    }
}

@Composable
fun WeekForecastItem(daily: Daily, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = daily.getDayFromTimeStamp(),
                fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                fontSize = 10.ssp,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = "${daily.temp?.max?.toInt()} °",
                fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                fontSize = 10.ssp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(start = 3.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${daily.temp?.min?.toInt()} °",
                fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                fontSize = 10.ssp,
                color = MaterialTheme.colorScheme.secondary,
            )
            AsyncImage(
                modifier = Modifier.size(15.sdp),
                model = "https://openweathermap.org/img/w/${daily.weather?.get(0)?.icon}.png",
                placeholder = painterResource(id = R.drawable.ic_weather_logo),
                contentDescription = stringResource(
                    id = R.string.weather_statue_image
                )
            )
        }

    }
}
