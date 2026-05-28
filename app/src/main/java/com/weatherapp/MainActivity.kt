package com.weatherapp
import androidx.activity.viewModels
import com.weatherapp.viewmodel.MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.weatherapp.ui.nav.BottomNavBar
import com.weatherapp.ui.nav.BottomNavItem
import com.weatherapp.ui.nav.MainNavHost
import com.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel : MainViewModel by viewModels()

        setContent {

            val navController = rememberNavController()

            WeatherAppTheme {

                Scaffold(

                    topBar = {

                        TopAppBar(

                            title = {
                                Text("Bem-vindo/a!")
                            },

                            actions = {

                                IconButton(
                                    onClick = { finish() }
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.AutoMirrored.Filled.ExitToApp,

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
                            navController = navController,
                            items = items
                        )
                    },

                    floatingActionButton = {

                        FloatingActionButton(
                            onClick = { }
                        ) {

                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Adicionar"
                            )
                        }
                    }

                ) { innerPadding ->

                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        MainNavHost(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}