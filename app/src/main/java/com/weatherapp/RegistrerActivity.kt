package com.weatherapp

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent

class RegistrerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegistrerPage()

        }
    }

    @Composable
    fun RegistrerPage(modifier: Modifier = Modifier) {
        var name by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var repetirsenha by rememberSaveable { mutableStateOf("") }
        val activity = LocalContext.current as Activity
        Column(

            modifier = modifier.padding(24.dp).fillMaxSize().size(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            val modifier = modifier.fillMaxWidth(fraction = 0.9f)

            Text(
                text = "Registre-se!",
                fontSize = 24.sp
            )
            OutlinedTextField(
                value = name,
                label = { Text(text = "Digite o seu nome!") },
                modifier = modifier,
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.size(12.dp))

            OutlinedTextField(
                value = email,
                label = { Text(text = "Digite seu e-mail") },
                modifier = modifier,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.size(12.dp))

            OutlinedTextField(
                value = password,
                label = { Text(text = "Digite sua senha") },
                modifier = modifier,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.size((12.dp)))

            OutlinedTextField(
                value = repetirsenha,
                label = { Text(text = "Repita sua senha") },
                modifier = modifier,
                onValueChange = { repetirsenha = it }
            )

            Spacer(modifier = Modifier.size((12.dp)))



            Row(modifier = modifier
                .padding(12.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(  onClick = {
                    Toast.makeText(activity,
                        "Registro OK!",
                        Toast.LENGTH_LONG
                    ).show()
                    activity.startActivity(
                        Intent(activity, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    )
                }, enabled = name.isNotEmpty() &&
                            email.isNotEmpty() &&
                            password.isNotEmpty() &&
                            repetirsenha.isNotEmpty() &&
                            password == repetirsenha
                ) {
                    Text("Registre-se!")
                }
                Button(
                    onClick = { name= ""; email = ""; password = ""; repetirsenha="" }
                ) {
                    Text("Limpar")
                }
            }
        }


    }

}