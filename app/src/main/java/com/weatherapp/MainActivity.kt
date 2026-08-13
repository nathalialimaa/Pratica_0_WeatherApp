package com.weatherapp
import com.weatherapp.viewmodel.MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weatherapp.ui.CityDialog
import com.weatherapp.ui.nav.BottomNavBar
import com.weatherapp.ui.nav.BottomNavItem
import com.weatherapp.ui.nav.MainNavHost
import com.weatherapp.ui.nav.Route
import com.weatherapp.ui.theme.WeatherAppTheme
import androidx.navigation.NavDestination.Companion.hasRoute
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.weatherapp.db.fb.FBDatabase
import com.weatherapp.model.User
import com.weatherapp.viewmodel.MainViewModelFactory
import com.weatherapp.api.WeatherService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import com.weatherapp.monitor.ForecastMonitor
import android.content.Intent
import androidx.compose.runtime.DisposableEffect
import androidx.core.util.Consumer

private fun handleNotificationIntent(
    intent: Intent,
    viewModel: MainViewModel
) {
    intent.getStringExtra("city")?.let { cityName ->

        viewModel.city = cityName
        viewModel.page = Route.Home
    }
}
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val fbDB = remember { FBDatabase() }
            val weatherService = remember { WeatherService(this@MainActivity) }
            val monitor = remember { ForecastMonitor(this@MainActivity) }

            val viewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(
                    fbDB,
                    weatherService,
                    monitor
                )
            )

            handleNotificationIntent(
                intent = intent,
                viewModel = viewModel
            )

            DisposableEffect(Unit) {

                val listener = Consumer<Intent> { intent ->

                    viewModel.city = intent.getStringExtra("city")

                    viewModel.page = Route.Home
                }

                addOnNewIntentListener(listener)

                onDispose {
                    removeOnNewIntentListener(listener)
                }
            }

            val navController = rememberNavController()
            var showDialog by remember { mutableStateOf(false) }

            val currentRoute = navController.currentBackStackEntryAsState()
            val showButton = currentRoute.value?.destination?.hasRoute(Route.List::class) == true
            val launcher = rememberLauncherForActivityResult(contract =
                ActivityResultContracts.RequestPermission(), onResult = {} )
            WeatherAppTheme {
                if (showDialog) {

                    CityDialog(
                        onDismiss = { showDialog = false },

                        onConfirm = { city ->

                            if (city.isNotBlank()) {
                                viewModel.addCity(city)
                            }

                            showDialog = false
                        }
                    )
                }
                Scaffold(

                    topBar = {

                        TopAppBar(

                            title = {
                                val name = viewModel.user?.name?:"[carregando...]"
                                Text("Bem-vindo/a! $name")
                            },

                            actions = {

                                IconButton(
                                    onClick = {

                                        Firebase.auth.signOut()

                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                        contentDescription = "Sair"
                                    )
                                }
                            }
                        )
                    },

                    bottomBar = {

                        val items = listOf(

                            BottomNavItem.HomeButton,
                            BottomNavItem.ListButton,
                            BottomNavItem.MapButton
                        )

                        BottomNavBar(
                            viewModel = viewModel,
                            navController = navController,
                            items = items
                        )
                    },

                    floatingActionButton = {
                        if (showButton) {
                            FloatingActionButton(onClick = { showDialog = true }) {
                                Icon(Icons.Default.Add, contentDescription = "Adicionar")
                            }
                        }
                    }

                ) { innerPadding ->

                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        launcher.launch(
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        )

                        MainNavHost(
                            navController = navController,
                            viewModel = viewModel
                        )

                        LaunchedEffect(viewModel.page) {

                            navController.navigate(viewModel.page) {

                                navController.graph.startDestinationRoute?.let {

                                    popUpTo(it) {
                                        saveState = true
                                    }

                                    restoreState = true
                                }

                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
        }
    }

}