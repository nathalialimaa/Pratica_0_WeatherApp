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

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {

    Column {

        if (viewModel.city == null) {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Blue)
                    .wrapContentSize(Alignment.Center)
            ) {

                Text(
                    text = "Selecione uma cidade!",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp
                )
            }

        } else {

            val weather = viewModel.weather(viewModel.city!!)

            Row {

                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )

                Column {

                    Spacer(modifier = Modifier.size(12.dp))

                    Text(
                        text = viewModel.city!!,
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
            }

            viewModel.forecast(viewModel.city!!)?.let { forecasts ->

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