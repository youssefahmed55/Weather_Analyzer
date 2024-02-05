package com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import com.weatheraanalyzerrrr.weatheranalyzer.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview(showBackground = true, showSystemUi = true, device = "id:Nexus One")
@Composable
fun MainScreen(navController: NavController? = null) {
    //BackPressHandler
    val activity = (LocalContext.current as? Activity)
    BackHandler {
        activity?.finish()
    }


    var searchTextFieldValue by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 20.sdp, start = 10.sdp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.sdp, end = 10.sdp),
            horizontalArrangement = Arrangement.spacedBy(10.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomSearchView(
                searchTextFieldValue,
                Modifier
                    .background(
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                        CircleShape
                    )
                    .weight(2f)
            ) { text ->
                searchTextFieldValue = text
            }

            Image(
                modifier = Modifier.clickable { navController?.navigate("weekForecast") },
                imageVector = Icons.Default.DateRange,
                contentDescription = stringResource(R.string.date_range_icon),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }

        Spacer(modifier = Modifier.size(10.sdp))
        LocationTitle("Lagos, Nigeria", Modifier.padding(start = 5.sdp))
        Spacer(modifier = Modifier.size(10.sdp))
        DateTitle("1 August 2023", modifier = Modifier.padding(start = 15.sdp))
        Spacer(modifier = Modifier.size(30.sdp))

        Column(modifier = Modifier.padding(start = 10.sdp, end = 10.sdp)) {
            TemperatureLayout("", "81°", "Mostly Clear")
            Spacer(modifier = Modifier.size(15.sdp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TemperatureFeature(stringResource(R.string.humidity), "25%")
                TemperatureFeature(stringResource(R.string.feels_like), "24°", Alignment.End)
            }
            Spacer(modifier = Modifier.size(10.sdp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TemperatureFeature(stringResource(R.string.pressure), "1008 mbar")
                TemperatureFeature(stringResource(R.string.wind), "102km/hr", Alignment.End)
            }
            Spacer(modifier = Modifier.size(10.sdp))

        }
        NextHours()
    }
}

@Composable
fun TemperatureLayout(imageResource: String, degree: String, statue: String) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.sdp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier.size(90.sdp),
            painter = painterResource(id = R.drawable.ic_weather_logo),
            contentDescription = stringResource(R.string.weather_statue_image)
        )
        Column(verticalArrangement = Arrangement.spacedBy(5.sdp)) {
            Text(
                text = degree,
                fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
                fontSize = 15.ssp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = statue,
                fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                fontSize = 15.ssp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}

@Composable
fun DateTitle(date: String, modifier: Modifier = Modifier) {

    Text(
        modifier = modifier,
        text = date,
        fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 10.ssp
    )
}

@Composable
fun LocationTitle(locationText: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Default.LocationOn,
            contentDescription = stringResource(R.string.location_icon),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Text(
            text = locationText,
            fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
            fontSize = 15.ssp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CustomSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        shape = CircleShape,
        value = search,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = stringResource(
                    R.string.search_icon
                ), tint = MaterialTheme.colorScheme.primary
            )
        },
        placeholder = { Text(text = stringResource(R.string.search_city)) },
        textStyle = TextStyle.Default.copy(
            fontSize = 10.ssp,
            fontFamily = FontFamily(Font(R.font.poppins_regular, FontWeight.Medium)),
            color = MaterialTheme.colorScheme.primary
        ),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Search
        )
    )


}

@Composable
fun TemperatureFeature(
    feature: String,
    resultValue: String,
    alignment: Alignment.Horizontal = Alignment.Start
) {

    Column(verticalArrangement = Arrangement.spacedBy(5.sdp), horizontalAlignment = alignment) {
        Text(
            text = feature,
            fontFamily = FontFamily(Font(R.font.poppins_regular, FontWeight.Normal)),
            fontSize = 10.ssp,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = resultValue,
            fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
            fontSize = 10.ssp,
            color = MaterialTheme.colorScheme.primary
        )
    }

}

@Composable
fun NextHours() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.sdp), verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Next Hours",
            fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
            fontSize = 15.ssp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(10.sdp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.sdp),
            contentPadding = PaddingValues(end = 5.sdp)
        ) {

            item {
                HourWeather()
            }
            item {
                HourWeather()
            }
            item {
                HourWeather()
            }
            item {
                HourWeather()
            }
            item {
                HourWeather()
            }
            item {
                HourWeather()
            }
        }

    }


}




