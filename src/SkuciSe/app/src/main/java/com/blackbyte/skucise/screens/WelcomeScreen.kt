package com.blackbyte.skucise.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.ui.theme.SkuciSeTheme


@Preview
@Composable
fun WelcomeScreen(
    navigateToSignUp: () -> Unit = {},
    returnToPreviousScreen: () -> Unit = {}
) {

    SkuciSeTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(all = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.skucise_icon),
                        contentScale = ContentScale.Inside,
                        contentDescription = "Skuci Se icon",
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
                Spacer(modifier = Modifier.size(size = 20.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Prodajte, izdajte i iznajmite stambene objekte.",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(182.dp)
                    )
                }
                Spacer(modifier = Modifier.size(size = 20.dp))
                Button(
                    onClick = {
                        navigateToSignUp()
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "registration icon",
                        Modifier.padding(end = 8.dp)
                    )
                    Text(text = "Registrujte se")
                }
                Spacer(modifier = Modifier.size(size = 20.dp))
                OutlinedButton(
                    onClick = {
                        // do something here
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = "registration icon",
                        Modifier.padding(end = 8.dp)
                    )
                    Text(text = "Prijavite se")
                }
            }
        }
    }
}