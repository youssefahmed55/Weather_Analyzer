package com.weatheraanalyzerrrr.weatheranalyzer.ui.screens.main

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import com.weatheraanalyzerrrr.weatheranalyzer.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.gson.Gson
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly
import com.weatheraanalyzerrrr.weatheranalyzer.ui.screens.Screen
import com.weatheraanalyzerrrr.weatheranalyzer.ui.screens.ViewModelStates
import com.weatheraanalyzerrrr.weatheranalyzer.ui.util.SimpleDialog
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

private const val TAG = "MainScreen"

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true, showSystemUi = true, device = "id:Nexus One")
@Composable
fun MainScreen(navController: NavController? = null, viewModel: MainViewModel = hiltViewModel()) {
    //BackPressHandler
    val activity = (LocalContext.current as? Activity)
    BackHandler {
        activity?.finish() //Finish Activity
    }


    //Initialize currentWeatherState
    val currentWeatherState by viewModel.currentWeatherModel.collectAsState()
    //Initialize hourlyState
    val hourlyState by viewModel.hourlyModel.collectAsState()
    //Initialize currentWeather
    val currentWeather = remember { mutableStateOf(CurrentModelResponse()) }
    //Initialize hourlyModels
    val hourlyModels = remember { mutableStateOf(emptyList<Hourly>()) }
    //Initialize snackBarHostState
    val snackBarHostState = remember { SnackbarHostState() }
    //Initialize showDialogState
    var showDialogState by remember { mutableStateOf(true) }
    //Initialize showProgressState1
    val showProgressState1 = remember { mutableStateOf(false) }
    //Initialize showProgressState2
    val showProgressState2 = remember { mutableStateOf(false) }
    //Initialize textSearch
    val textSearch by viewModel.textSearch.collectAsState()
    //Initialize errorMessage
    val errorMessage by viewModel.errorMessage.collectAsState()

    //Initialize locationPermissions To Check Location Permissions
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )


    //Check Location Permissions
    LaunchedEffect(
        key1 = locationPermissions.allPermissionsGranted,
        key2 = viewModel.currentWeatherModel
    ) {
        if (locationPermissions.allPermissionsGranted) {
            Log.d(TAG, "allPermissionsGranted")
            showDialogState = false         //Set False to showDialogState
            viewModel.getCurrentLocation()  //Get Current Location
        } else {
            Log.d(TAG, "allPermissionsDenied")
            viewModel.getDefaultLocation()  //Get Default Location
        }

    }

    //Check Error Message Is Not Empty
    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage.isNotEmpty()) {
            snackBarHostState
                .showSnackbar(
                    message = errorMessage,
                    // Defaults to SnackBarDuration.Short
                    duration = SnackbarDuration.Short
                )

        }
    }


    //Observer Current Weather Data
    ObserverCurrentWeatherData(
        currentWeatherState,
        currentWeather,
        snackBarHostState,
        showProgressState1
    )
    //Observer Hourly Weather Data
    ObserverHourlyWeatherData(hourlyState, hourlyModels, snackBarHostState, showProgressState2)

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(top = 20.sdp, start = 10.sdp),
        snackbarHost = { SnackbarHost(snackBarHostState) }) { contentPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
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
                        textSearch,
                        errorMessage,
                        Modifier
                            .testTag("searchTextFieldTag")
                            .background(
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                                CircleShape
                            )
                            .weight(2f), onValueChange = { text ->
                            viewModel.setSearchText(text)
                        })

                    Image(
                        modifier = Modifier
                            .testTag("calenderIcon")
                            .clickable {
                                viewModel.setSearchText("")
                                val userJson = Gson().toJson(currentWeather.value)
                                Log.d(TAG, "MainScreen: $userJson")
                                navController?.navigate(
                                    "${Screen.WeekForecastScreen.route}/${userJson}"
                                )
                            },
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.date_range_icon),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }

                Spacer(modifier = Modifier.size(10.sdp))
                LocationTitle(
                    locationText = currentWeather.value.name,
                    modifier = Modifier.padding(start = 5.sdp)
                )
                Spacer(modifier = Modifier.size(10.sdp))
                DateTitle(
                    date = currentWeather.value.getDayFromTimeStamp(),
                    modifier = Modifier.padding(start = 15.sdp)
                )
                Spacer(modifier = Modifier.size(30.sdp))

                Column(modifier = Modifier.padding(start = 10.sdp, end = 10.sdp)) {
                    Box {
                        TemperatureLayout(
                            currentWeather.value.weather?.get(0)?.icon,
                            currentWeather.value.main?.temp?.toInt(),
                            currentWeather.value.weather?.get(0)?.description
                        )
                        if (showProgressState1.value)
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    Spacer(modifier = Modifier.size(15.sdp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TemperatureFeature(
                            stringResource(R.string.humidity),
                            currentWeather.value.main?.humidity.toString() + " %"
                        )
                        TemperatureFeature(
                            stringResource(R.string.feels_like),
                            currentWeather.value.main?.feels_like?.toInt().toString() + " °",
                            Alignment.End
                        )
                    }
                    Spacer(modifier = Modifier.size(10.sdp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TemperatureFeature(
                            stringResource(R.string.pressure),
                            currentWeather.value.main?.pressure.toString() + " " + stringResource(R.string.mbar)
                        )
                        TemperatureFeature(
                            stringResource(R.string.wind),
                            currentWeather.value.wind?.speed.toString() + " " + stringResource(R.string.km_hr),
                            Alignment.End
                        )
                    }
                    Spacer(modifier = Modifier.size(10.sdp))

                }
                Box {
                    NextHours(hourlyModels.value)
                    if (showProgressState2.value)
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

            }


            SimpleDialog(
                showDialog = showDialogState,
                stringResource(R.string.location_permission),
                stringResource(R.string.we_need_location_permissions_for_this_application),
                onClose = { showDialogState = false },
                onAccept = {
                    locationPermissions.launchMultiplePermissionRequest()
                    showDialogState = false


                },
                modifier = Modifier.align(Alignment.Center)
            )
        }


    }
}


@Composable
fun ObserverHourlyWeatherData(
    hourlyState: ViewModelStates<List<Hourly>>,
    hourlyModels: MutableState<List<Hourly>>,
    snackBarHostState: SnackbarHostState,
    showProgressState2: MutableState<Boolean>
) {
    LaunchedEffect(hourlyState) {
        when (hourlyState) {
            is ViewModelStates.Success -> {
                showProgressState2.value = false
                hourlyModels.value = hourlyState.data
            }

            is ViewModelStates.Error -> {
                Log.d(TAG, "Error Hourly")
                showProgressState2.value = false
                hourlyModels.value = hourlyState.data ?: emptyList()

                snackBarHostState
                    .showSnackbar(
                        message = hourlyState.message,
                        actionLabel = "Skip",
                        // Defaults to SnackBarDuration.Short
                        duration = SnackbarDuration.Short
                    )
            }

            else -> {
                showProgressState2.value = true
            }
        }
    }
}

@Composable
fun ObserverCurrentWeatherData(
    state: ViewModelStates<CurrentModelResponse>,
    currentWeather: MutableState<CurrentModelResponse>,
    snackBarHostState: SnackbarHostState,
    showProgressState1: MutableState<Boolean>
) {

    LaunchedEffect(state) {
        when (state) {
            is ViewModelStates.Success -> {
                showProgressState1.value = false
                currentWeather.value =
                    state.data
            }

            is ViewModelStates.Error -> {
                Log.d(TAG, "Error Current")
                showProgressState1.value = false

                state.data?.let {
                    currentWeather.value = it
                }

                snackBarHostState
                    .showSnackbar(
                        message = state.message,
                        actionLabel = "Skip",
                        // Defaults to SnackBarDuration.Short
                        duration = SnackbarDuration.Short
                    )


            }

            else -> {
                showProgressState1.value = true
            }
        }


    }


}

@Composable
fun TemperatureLayout(imageResource: String? = "", degree: Int? = 0, statue: String? = "") {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.sdp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            modifier = Modifier.size(90.sdp),
            model = "https://openweathermap.org/img/w/$imageResource.png",
            error = painterResource(id = R.drawable.ic_weather_logo),
            contentDescription = stringResource(R.string.weather_statue_image)
        )
        Column(verticalArrangement = Arrangement.spacedBy(5.sdp)) {
            Text(
                text = if (degree != null) "$degree °" else " °",
                fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
                fontSize = 15.ssp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = statue ?: "",
                fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                fontSize = 15.ssp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}

@Composable
fun DateTitle(modifier: Modifier = Modifier, date: String = "") {

    Text(
        modifier = modifier.testTag("Date"),
        text = date,
        fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 10.ssp
    )
}

@Composable
fun LocationTitle(modifier: Modifier = Modifier, locationText: String? = "") {
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
            text = locationText ?: "",
            fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
            fontSize = 15.ssp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .testTag("cityName_main")
                .padding(top = 5.sdp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomSearchView(
    search: String,
    errorMessage: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current


    OutlinedTextField(
        modifier = modifier,
        shape = CircleShape,
        value = search,
        onValueChange = onValueChange,
        isError = errorMessage.isNotEmpty(),
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
            imeAction = ImeAction.Search,

            ),
        trailingIcon = {
            if (errorMessage.isNotEmpty())
                Icon(Icons.Filled.Info, "error", tint = MaterialTheme.colorScheme.error)
        },
        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() })
    )


}


@Composable
fun TemperatureFeature(
    feature: String = "",
    resultValue: String = "",
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
            text = if (!resultValue.contains("null")) resultValue else "",
            fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
            fontSize = 10.ssp,
            color = MaterialTheme.colorScheme.primary
        )
    }

}

@Composable
fun NextHours(hourlyModels: List<Hourly>?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.sdp), verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.next_hours),
            fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
            fontSize = 15.ssp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(10.sdp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.sdp),
            contentPadding = PaddingValues(end = 5.sdp)
        ) {
            items(hourlyModels ?: emptyList()) {
                HourWeather(
                    it.getTimeFromTimeStamp(),
                    it.weather?.get(0)?.icon,
                    it.temp?.toInt().toString() + " °"
                )
            }
        }

    }


}









