package com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens

import android.view.animation.OvershootInterpolator
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
import com.weatheraanalyzerrrr.weatheranalyzer.R

@Composable
fun SplashScreenAnimate(navController: NavController) {


    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = 0.5f, animationSpec = tween(
            durationMillis = 1000,
            easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            }

        ))

        delay(2000L)
        navController.navigate("main")
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
                .scale(scale.value).align(Alignment.Center)
        )


    }
}