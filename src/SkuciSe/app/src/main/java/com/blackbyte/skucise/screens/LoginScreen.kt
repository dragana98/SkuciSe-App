package com.blackbyte.skucise.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.components.OutlinedInputField
import com.blackbyte.skucise.components.OutlinedPasswordField
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

@Preview
@Composable
fun LoginScreen(returnToPreviousScreen: () -> Unit = {}) {
    SkuciSeTheme {
        Scaffold(
            topBar = { NavTopBar("Prijava", returnToPreviousScreen = returnToPreviousScreen) },
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight() // verticalArrangement = Arrangement.Center
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

                OutlinedInputField("E-adresa", modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.size(size = 20.dp))

                OutlinedPasswordField("Lozinka", modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.size(size = 10.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
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
                        //navigateToSignUp()
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
                ){

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                        //horizontalArrangement = Arrangement.End,
                        //modifier = Modifier.fillMaxWidth()
                    ){
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
        }
    }
}