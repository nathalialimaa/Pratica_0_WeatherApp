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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginPage()

        }
    }
    @Preview(showBackground = true)
    @Composable
    fun LoginPage(modifier: Modifier = Modifier) {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        val context = LocalContext.current
        Column(

            modifier = modifier.padding(24.dp).fillMaxSize().size(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            val modifier = modifier.fillMaxWidth(fraction = 0.9f)

            Text(
                text = "Bem-vindo/a!",
                fontSize = 24.sp
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

            Row(modifier = modifier
                .padding(12.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(  onClick = {
                    Toast.makeText(context, "Login OK!", Toast.LENGTH_LONG).show()

                }, enabled = email.isNotEmpty() && password.isNotEmpty()
                ) {
                    Text("Login")
                }
                Button(
                    onClick = { email = ""; password = "" }
                ) {
                    Text("Limpar")
                }
            }
        }
    }
}