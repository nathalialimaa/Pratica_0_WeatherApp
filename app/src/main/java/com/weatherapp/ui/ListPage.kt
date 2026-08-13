package com.weatherapp.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.model.City
import com.weatherapp.model.Weather
import com.weatherapp.viewmodel.MainViewModel
import com.weatherapp.ui.nav.Route
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.weatherapp.R



@Composable
fun ListPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
    ) {
    val cityList = viewModel.cities

    val activity = LocalContext.current as Activity

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        items(cityList, key = { it.name }) { city ->

            CityItem(

                city = city,
                weather = viewModel.weather(city.name),

                onClick = {

                    viewModel.city = city.name
                    viewModel.page = Route.Home

                    Toast.makeText(
                        activity,
                        "Cidade: ${city.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                },

                onClose = {

                    Toast.makeText(
                        activity,
                        "Remover: ${city.name}",
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.remove(city)

                }
            )
        }
    }
}

@Composable
fun CityItem(
    city: City,
    weather: Weather,
    onClick: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val desc =
        if (weather == Weather.LOADING)
            "Carregando clima..."
        else
            weather.desc

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },

        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = weather.imgUrl,
            modifier = Modifier.size(75.dp),
            error = painterResource(id = R.drawable.loading),
            contentDescription = "Imagem"
        )

        Spacer(modifier = Modifier.size(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = city.name,
                fontSize = 24.sp
            )

            Text(
                text = desc,
                fontSize = 16.sp
            )
        }

        IconButton(onClick = onClose) {

            Icon(
                Icons.Filled.Close,
                contentDescription = "Close"
            )
        }
    }
}