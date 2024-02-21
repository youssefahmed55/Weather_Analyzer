package com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.weekforecast

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.weatheraanalyzerrrr.weatheranalyzer.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview(showBackground = true, showSystemUi = true, device = "id:Nexus One")
@Composable
fun WeekForecast(
    navController: NavController? = null,
    viewModel: WeekForecastViewModel = hiltViewModel()
) {

    val cityNameState by viewModel.cityNameState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 20.sdp, start = 10.sdp, end = 10.sdp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { navController?.popBackStack() },
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Text(
                text = cityNameState,
                fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
                fontSize = 15.ssp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Center)
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
            item { WeekForecastItem(Modifier.padding(bottom = 5.sdp)) }
            item { WeekForecastItem(Modifier.padding(bottom = 5.sdp)) }
            item { WeekForecastItem(Modifier.padding(bottom = 5.sdp)) }
            item { WeekForecastItem(Modifier.padding(bottom = 5.sdp)) }
            item { WeekForecastItem(Modifier.padding(bottom = 5.sdp)) }
            item { WeekForecastItem(Modifier.padding(bottom = 5.sdp)) }
            item { WeekForecastItem(Modifier.padding(bottom = 5.sdp)) }
            item { WeekForecastItem() }
        }

    }

}

@Composable
fun WeekForecastItem(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sunday",
                fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                fontSize = 10.ssp,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = "24°",
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
                text = "18°",
                fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                fontSize = 10.ssp,
                color = MaterialTheme.colorScheme.secondary,
            )
            Image(
                modifier = Modifier.size(15.sdp),
                painter = painterResource(id = R.drawable.ic_weather_logo),
                contentDescription = stringResource(
                    id = R.string.weather_statue_image
                )
            )
        }

    }
}
