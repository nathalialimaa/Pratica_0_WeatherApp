package com.weatherapp.viewmodel

import android.location.Location
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.weatherapp.db.fb.FBCity
import com.weatherapp.db.fb.FBDatabase
import com.weatherapp.db.fb.FBUser
import com.weatherapp.db.fb.toFBCity
import com.weatherapp.model.City
import com.weatherapp.model.User
import com.weatherapp.ui.ListPage
import com.weatherapp.api.WeatherService
import com.weatherapp.api.toWeather
import com.weatherapp.model.Weather
import com.weatherapp.api.toForecast
import com.weatherapp.model.Forecast
import com.weatherapp.ui.nav.Route


class MainViewModel(
    private val db: FBDatabase,
    private val service: WeatherService
) : ViewModel(), FBDatabase.Listener {
    private val _cities = mutableStateMapOf<String, City>()
    val cities: List<City>
        get() = _cities.values
            .toList()
            .sortedBy { it.name }

    private val _weather =
        mutableStateMapOf<String, Weather>()

    private val _forecast =
        mutableStateMapOf<String, List<Forecast>?>()

    private var _city =
        mutableStateOf<String?>(null)
    private var _page = mutableStateOf<Route>(Route.Home)

    var page: Route
        get() = _page.value
        set(value) {
            _page.value = value
        }
    var city: String?
        get() = _city.value
        set(value) {
            _city.value = value
        }

    private val _user = mutableStateOf<User?> (null)
    val user : User?
        get() = _user.value

    init {
        db.setListener(this)
    }

    fun remove(city: City) {
        db.remove(city.toFBCity())
    }

    fun update(city: City) {
        db.update(city.toFBCity())
    }

    fun addCity(name: String) {

        service.getLocation(name) { lat, lng ->

            if (lat != null && lng != null) {

                db.add(
                    City(
                        name = name,
                        location = LatLng(lat, lng)
                    ).toFBCity()
                )
            }
        }
    }

    fun addCity(location: LatLng) {

        service.getName(location.latitude, location.longitude) { name ->

            if (name != null) {

                db.add(
                    City(
                        name = name,
                        location = location
                    ).toFBCity()
                )
            }
        }
    }

    override fun onUserLoaded(user: FBUser) {
        _user.value = user.toUser()
    }

    override fun onUserSignOut() {
        //TODO("Not yet implemented")
        _user.value = null
        _cities.clear()
    }

    override fun onCityAdded(city: FBCity) {
        _cities[city.name!!] = city.toCity()
    }

    override fun onCityUpdated(city: FBCity) {
        _cities.remove(city.name)

        _cities[city.name!!] =
            city.toCity()
    }

    override fun onCityRemoved(city: FBCity) {
        _cities.remove(city.name)
    }

    fun weather(name: String) =
        _weather.getOrPut(name) {
            loadWeather(name)
            Weather.LOADING
        }

    private fun loadWeather(name: String) {
        service.getWeather(name) { apiWeather ->
            apiWeather?.let {
                _weather[name] = apiWeather.toWeather()
                loadBitmap(name)
            }
        }
    }

    private fun loadBitmap(name: String) {
        _weather[name]?.let { weather ->
            service.getBitmap(weather.imgUrl) { bitmap ->
                _weather[name] = weather.copy(bitmap = bitmap)
            }
        }
    }

    fun forecast(name: String) =
        _forecast.getOrPut(name) {

            loadForecast(name)

            emptyList()
        }

    private fun loadForecast(name: String) {

        service.getForecast(name) { apiForecast ->

            apiForecast?.let {

                _forecast[name] = apiForecast.toForecast()
            }
        }
    }

}