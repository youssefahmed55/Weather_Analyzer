package com.weatheraanalyzerrrr.weatheranalyzer.ui.screens


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.weatheraanalyzerrrr.weatheranalyzer.R
import com.weatheraanalyzerrrr.weatheranalyzer.ui.util.SimpleDialog
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay


private const val TAG = "SplashScreen"

@Composable
fun SplashScreenAnimate(navController: NavController) {

    //Initialize scale
    val scale = remember { Animatable(0f) }

    //Initialize context
    val context = LocalContext.current


    //Initialize showDialogState
    var showDialogState by remember { mutableStateOf(false) }

    //Initialize settingResultRequest To Check Location Enabled
    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->

        if (activityResult.resultCode == Activity.RESULT_OK) {

            Log.d(TAG, "Accepted")
            showDialogState = true
        } else {
            Log.d(TAG, "Denied")
            showDialogState = false
            navController.navigate(Screen.MainScreen.route)  //Navigate to MainScreen

        }

    }

    //Initialize startForResult To Check Back From Google Maps App
    val startForResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "Accepted")
            showDialogState = false
            navController.navigate(Screen.MainScreen.route)  //Navigate to MainScreen
        } else {
            Log.d(TAG, "Denied")
            showDialogState = false
            navController.navigate(Screen.MainScreen.route)  //Navigate to MainScreen
        }
    }



    LaunchedEffect(key1 = true) {

        scale.animateTo(targetValue = 0.5f, animationSpec = tween(
            durationMillis = 1000,
            easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            }

        ))
        checkLocationSetting(
            context = context,
            onDisabled = { intentSenderRequest ->
                settingResultRequest.launch(intentSenderRequest)  //launch intentSenderRequest
            },
            onEnabled = {
                showDialogState = true
            }
        )
        delay(2000L)

    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_weather_logo),
            contentDescription = null,
            modifier = Modifier
                .width(450.sdp)
                .height(450.sdp)
                .scale(scale.value)
                .align(Alignment.Center)
        )

        SimpleDialog(
            showDialog = showDialogState,
            stringResource(R.string.location_permission),
            stringResource(R.string.it_is_required_to_run_the_google_map_application_to_find_your_location),
            onClose = {
                showDialogState = false
                navController.navigate(Screen.MainScreen.route)  //Navigate to MainScreen
            },
            onAccept = {
                startForResult.launch(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps")
                    )
                ) //Intent To Google Maps App
            },
            modifier = Modifier.align(Alignment.Center)
        )


    }
}

fun checkLocationSetting(
    context: Context,
    onDisabled: (IntentSenderRequest) -> Unit,
    onEnabled: () -> Unit
) {

    val locationRequest = LocationRequest.create().apply {
        interval = 1000
        fastestInterval = 1000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val builder: LocationSettingsRequest.Builder = LocationSettingsRequest
        .Builder()
        .addLocationRequest(locationRequest)

    val gpsSettingTask: Task<LocationSettingsResponse> =
        client.checkLocationSettings(builder.build())

    gpsSettingTask.addOnSuccessListener { onEnabled() }
    gpsSettingTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest
                    .Builder(exception.resolution)
                    .build()
                onDisabled(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                // ignore here
            }
        }
    }

}