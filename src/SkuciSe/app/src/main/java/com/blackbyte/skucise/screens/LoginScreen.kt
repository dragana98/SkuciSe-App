package com.blackbyte.skucise.screens


import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.MainActivity.Companion.prefs
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.components.OutlinedInputField
import com.blackbyte.skucise.components.OutlinedPasswordField
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.utils.Utils
import org.json.JSONObject
import org.json.JSONTokener

@Composable
fun LoginScreen(
    returnToPreviousScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    ) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { NavTopBar("Prijava", returnToPreviousScreen = returnToPreviousScreen) },
        backgroundColor = MaterialTheme.colors.background
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }

        val isFormValid by derivedStateOf{
             email.isNotBlank() && password.length > 6
        }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState()) // verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.size(size = 40.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.skucise_icon),
                    contentScale = ContentScale.Inside,
                    contentDescription = "SkuciSe Icon",
                    modifier = Modifier.size(180.dp, 180.dp)
                )
            }
            Spacer(modifier = Modifier.size(size = 20.dp))

            Text(
                text = "Skući Se",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(size = 20.dp))

            Image(
                painter = painterResource(id = R.drawable.separator),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Skuci Se icon",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(size = 30.dp))

            OutlinedInputField(
                "E-adresa",
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(size = 20.dp))
            OutlinedPasswordField(
                "Lozinka",
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(size = 10.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Zaboravili ste lozinku?",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Right,
                    color = MaterialTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.size(size = 50.dp))

            Button(
                onClick = {
                    Utils.Requests.login(
                        username = email,
                        password = password,
                        onFinish = fun(body: String, responseCode: Int): Unit {
                            if (responseCode == 200) {
                                val jsonObject = JSONTokener(body).nextValue() as JSONObject
                                val token = jsonObject.getString("token")
                                val id = jsonObject.getInt("id")
                                Handler(Looper.getMainLooper()).post(Runnable {
                                    prefs?.authToken = token
                                    prefs?.username = email
                                    prefs?.id = id

                                    navigateToHomeScreen()
                                })
                            } else {
                                Handler(Looper.getMainLooper()).post(Runnable {
                                    showSnackbar = true
                                })
                            }
                        })
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Prijava", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.size(size = 20.dp))

            // Registruj se
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                    //horizontalArrangement = Arrangement.End,
                    //modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "registration icon",
                        modifier = Modifier.size(17.dp),
                        tint = MaterialTheme.colors.primary
                    )
                    Text("Registrujte se ", color = MaterialTheme.colors.primary)
                }

                Text(
                    text = "ako nemate nalog.",
                    textAlign = TextAlign.Left,
                    //modifier = Modifier.width(100.dp)
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
        }
        if (showSnackbar) {
            Snackbar(
                action = {
                    Button(onClick = {
                        showSnackbar = false
                    }) {
                        Text("Pokušajte ponovo")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) { Text(text = "Uneti podaci nisu ispravni.") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SkuciSeTheme {
        LoginScreen({}, {}, {})
    }
}