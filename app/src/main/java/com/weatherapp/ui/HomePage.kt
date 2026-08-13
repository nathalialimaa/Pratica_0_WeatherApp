package com.weatherapp.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.viewmodel.MainViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.weatherapp.R
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.size

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {

    val selectedCity = viewModel.city?.let { name ->
        viewModel.cities.find { it.name == name }
    }

    if (selectedCity == null) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Blue)
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Selecione uma cidade...",
                    fontSize = 28.sp
                )
            }
        }

    } else {

        val weather = viewModel.weather(selectedCity.name)

        Column(
            modifier = modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = weather.imgUrl,
                    modifier = Modifier.size(140.dp),
                    error = painterResource(id = R.drawable.loading),
                    contentDescription = "Imagem"
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Spacer(modifier = Modifier.size(12.dp))

                    Text(
                        text = selectedCity.name,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                    Text(
                        text = weather.desc,
                        fontSize = 22.sp
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                    Text(
                        text = "Temp: ${weather.temp}℃",
                        fontSize = 22.sp
                    )
                }

                // SINO DE MONITORAMENTO
                val icon =
                    if (selectedCity.isMonitored)
                        Icons.Filled.Notifications
                    else
                        Icons.Outlined.Notifications

                Icon(
                    imageVector = icon,
                    contentDescription = "Monitorada?",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            viewModel.update(
                                selectedCity.copy(
                                    isMonitored = !selectedCity.isMonitored
                                )
                            )
                        }
                )
            }

            viewModel.forecast(selectedCity.name)?.let { forecasts ->

                LazyColumn {

                    items(forecasts) { forecast ->

                        ForecastItem(
                            forecast = forecast,
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}