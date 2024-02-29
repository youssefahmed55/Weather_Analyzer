package com.weatheraanalyzerrrr.weatheranalyzer.ui.screens.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.weatheraanalyzerrrr.weatheranalyzer.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview(showBackground = true)
@Composable
fun HourWeather(time: String = "04:00 am", image: String? = "", degree: String = "24°") {

    Column(
        modifier = Modifier
            .height(100.sdp)
            .width(70.sdp)
            .background(
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                RoundedCornerShape(5.sdp)
            )
            .padding(5.sdp),
        verticalArrangement = Arrangement.spacedBy(10.sdp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = time,
            fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
            fontSize = 10.ssp,
            color = MaterialTheme.colorScheme.primary
        )
        AsyncImage(
            modifier = Modifier.size(20.sdp),
            model = "https://openweathermap.org/img/w/$image.png",
            error = painterResource(id = R.drawable.ic_weather_logo),
            contentDescription = stringResource(
                id = R.string.weather_statue_image
            )
        )
        Text(
            text = degree,
            fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
            fontSize = 10.ssp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
