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
import com.weatherapp.ui.DataField
import com.weatherapp.ui.PasswordField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

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
            DataField(
                value = name,
                label = "Digite seu nome",
                modifier = modifier,
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.size(12.dp))

            DataField(
                value = email,
                label = "Digite seu e-mail",
                modifier = modifier,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.size(12.dp))

            PasswordField(
                value = password,
                label = "Digite sua senha",
                modifier = modifier,
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.size((12.dp)))

            PasswordField(
                value = repetirsenha,
                label = "Repita sua senha",
                modifier = modifier,
                onValueChange = { repetirsenha = it }
            )

            Spacer(modifier = Modifier.size((12.dp)))



            Row(modifier = modifier
                .padding(12.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(  onClick = {
                    Firebase.auth
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity) { task ->

                            if (task.isSuccessful) {

                                Toast.makeText(
                                    activity,
                                    "Registro OK!",
                                    Toast.LENGTH_LONG
                                ).show()

                                activity.finish()

                            } else {

                                Toast.makeText(
                                    activity,
                                    "Registro FALHOU!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
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