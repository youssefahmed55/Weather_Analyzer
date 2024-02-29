package com.weatheraanalyzerrrr.weatheranalyzer.ui.screens


import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.weatheraanalyzerrrr.weatheranalyzer.R


private const val TAG = "SplashScreen"

@Composable
fun SplashScreenAnimate(navController: NavController) {

    //Initialize scale
    val scale = remember { Animatable(0f) }

    //Initialize context
    val context = LocalContext.current

    //Check Location Enabled
    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "Accepted")
            navController.navigate(Screen.MainScreen.route)  //Navigate to MainScreen
        } else {
            Log.d(TAG, "Denied")
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
                navController.navigate(Screen.MainScreen.route)   //Navigate to MainScreen
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