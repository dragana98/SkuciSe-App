package com.blackbyte.skucise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkuciSeTheme {
                // A surface container using the 'background' color from the theme
                ScreenContents()
            }
        }
    }
}

@Composable
fun ScreenContents() {
    /*val allScreens = SkuciSeScreen.values().toList()
    var currentScreen by rememberSaveable { mutableStateOf(SkuciSeScreen.Overview) }
    val navController = rememberNavController()*/

    Surface(color = MaterialTheme.colors.background) {
        Column (modifier = Modifier.fillMaxHeight().padding(all = 20.dp), verticalArrangement = Arrangement.Center) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.skucise_icon), contentScale = ContentScale.Inside, contentDescription = "Skuci Se icon", modifier = Modifier.size(180.dp, 180.dp))
            }
            Box(modifier = Modifier.size(size = 20.dp)){}
            Text(text = "Skući Se", fontSize = 36.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Box(modifier = Modifier.size(size = 20.dp)){}
            Image(painter = painterResource(id = R.drawable.separator), contentScale = ContentScale.Fit, contentDescription = "Skuci Se icon", modifier = Modifier.fillMaxWidth())
            Box(modifier = Modifier.size(size = 20.dp)){}
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Prodajte, izdajte i iznajmite stambene objekte.", fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.width(148.dp))
            }
            Box(modifier = Modifier.size(size = 20.dp)){}
            Button(
                onClick = {
                    // do something here
                }
                , modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "registration icon",
                    Modifier.padding(end = 8.dp)
                )
                Text(text = "Registrujte se")
            }
            Box(modifier = Modifier.size(size = 20.dp)){}
            OutlinedButton(
                onClick = {
                    // do something here
                }
                , modifier = Modifier.fillMaxWidth()) {
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

@Composable
fun OutlinedInputField(label: String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SkuciSeTheme {
        ScreenContents()
    }
}