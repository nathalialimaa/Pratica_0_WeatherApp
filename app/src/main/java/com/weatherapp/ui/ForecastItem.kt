package com.weatherapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.model.Forecast
import java.text.DecimalFormat

@Composable
fun ForecastItem(
    forecast: Forecast,
    modifier: Modifier = Modifier,
    onClick: (Forecast) -> Unit
) {

    val format = DecimalFormat("#.0")

    val tempMin = format.format(forecast.tempMin)
    val tempMax = format.format(forecast.tempMax)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                onClick(forecast)
            },

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = forecast.weather,
                fontSize = 24.sp
            )

            Row {

                Text(
                    text = forecast.date,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.size(12.dp))
            }
        }

        Column {

            Text(
                text = "Min: $tempMin℃",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = "Max: $tempMax℃",
                fontSize = 16.sp
            )
        }
    }
}