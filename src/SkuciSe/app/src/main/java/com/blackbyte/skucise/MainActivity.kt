package com.blackbyte.skucise

import android.os.Bundle
import android.widget.CalendarView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.screens.InboxScreen
import com.blackbyte.skucise.screens.SignUpScreen
import com.blackbyte.skucise.screens.WelcomeScreen
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.google.android.material.datepicker.MaterialDatePicker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkuciSeTheme {
                InboxScreen()
            }
        }
    }

    @Composable
    fun AppNavigator() {
        val navController = rememberNavController()

        var navigateToSignUp = fun() {
            navController.navigate(route = "signUp")
        }

        var returnToPreviousScreen = fun() {
            navController.popBackStack()
        }


        //                                           v~~~~~ CHANGE THIS
        NavHost(navController = navController, startDestination = "welcome") {
            composable("welcome") {
                WelcomeScreen(navigateToSignUp, returnToPreviousScreen)
            }
            // EXAMPLE, WITH PASSING DATA TO A PAGE VIEW:
            /*
            composable("pageName/{argument}",
                       arguments = listOf(navArgument("argument") { type = NavType.StringType })
            ) { backStrackEntry ->
                // Serialize object to GSON, add GSON dependency to gradle
                // ... ...

                PageName(argument = stringNameGoesHere)
            }
            */
            composable("signUp") {
                SignUpScreen(returnToPreviousScreen)
            }

        }
    }
}


