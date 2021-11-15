package com.blackbyte.skucise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blackbyte.skucise.data.DrawerEntry
import com.blackbyte.skucise.screens.*
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkuciSeTheme {
                val navController = rememberNavController()
                AppNavigator(navController = navController)
                //ScheduledToursScreenPreview()
                //SearchScreenPreview()
                //AdvertiseScreenPreview()
            }
        }
    }

    @Composable
    fun AppNavigator(navController: NavHostController) {
        var returnToPreviousScreen = fun() {
            navController.popBackStack()
        }

        var toHomeScreen = fun() {
            navController.navigate(route = "home") {
                launchSingleTop = true
            }
        }
        var toInbox = fun() {
            navController.navigate(route = "inbox") {
                launchSingleTop = true
            }
        }
        var toLogin = fun() {
            navController.navigate(route = "login") {
                launchSingleTop = true
            }
        }
        var toMessages = fun() {
            navController.navigate(route = "messages") {
                launchSingleTop = true
            }
        }
        var toMyAccount = fun() {
            navController.navigate(route = "myAccount") {
                launchSingleTop = true
            }
        }
        var toPropertyEntry = fun() {
            navController.navigate(route = "propertyEntry") {
                launchSingleTop = true
            }
        }
        var toSavedEntries = fun() {
            navController.navigate(route = "savedEntries") {
                launchSingleTop = true
            }
        }
        var toSignUp = fun() {
            navController.navigate(route = "signUp") {
                launchSingleTop = true
            }
        }
        var toWelcome = fun() {
            navController.navigate(route = "welcome") {
                launchSingleTop = true
            }
        }
        var toScheduledTours = fun(){
            navController.navigate(route = "scheduledTours"){
                launchSingleTop = true
            }
        }
        var toSearch = fun(){
            navController.navigate(route = "search"){
                launchSingleTop = true
            }
        }
        var toAdvertise = fun(){
            navController.navigate(route = "advertise"){
                launchSingleTop = true
            }
        }


        //                                           v~~~~~ CHANGE THIS TO REFLECT IF USER IS LOGGED IN OR NOT
        NavHost(navController = navController, startDestination = "welcome") {
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
            composable("home") {
                HomeScreen(
                    drawerOptions = listOf(
                        DrawerEntry(
                            label = "Moj nalog",
                            icon = Icons.Filled.AccountCircle,
                            onTap = {toMyAccount()}
                        ),
                        DrawerEntry(
                            label = "Oglasi",
                            icon = Icons.Filled.House,
                            onTap = {toAdvertise()}
                        ),
                        DrawerEntry(
                            label = "Sačuvani oglasi",
                            icon = Icons.Filled.Favorite,
                            onTap = {toSavedEntries()}
                        ),
                        DrawerEntry(
                            label = "Poruke",
                            icon = Icons.Filled.Email,
                            onTap = {toMessages()}
                        ),
                        DrawerEntry(
                            label = "Zakazani obilasci",
                            icon = Icons.Filled.DateRange,
                            onTap = {toScheduledTours()}
                        ),
                        DrawerEntry(
                            label = "Podešavanja",
                            icon = Icons.Filled.Settings,
                            onTap = { /* TODO */}
                        ),
                        DrawerEntry(
                            label = "Odjava",
                            icon = Icons.Filled.ExitToApp,
                            onTap = { /* TODO */}
                        )

                    ),
                    returnToPreviousScreen = returnToPreviousScreen,
                    navigateToPropertyEntry = toPropertyEntry,
                    navigateToSavedEntries = toSavedEntries,
                    navigateToScheduledTours = toScheduledTours,
                    navigateToSearch = toSearch,
                    navigateToAdvertise = toAdvertise

                )
            }
            composable("inbox") {
                InboxScreen(returnToPreviousScreen = returnToPreviousScreen)
            }
            composable("login") {
                LoginScreen(
                    returnToPreviousScreen = returnToPreviousScreen,
                    navigateToHomeScreen = toHomeScreen
                )
            }
            composable("messages") {
                MessagesScreen(
                    returnToPreviousScreen = returnToPreviousScreen,
                    navigateToInbox = toInbox
                )
            }
            composable("myAccount") {
                MyAccountScreen(returnToPreviousScreen = returnToPreviousScreen)
            }
            composable("propertyEntry") {
                PropertyEntryScreen(returnToPreviousScreen = returnToPreviousScreen)
            }
            composable("savedEntries") {
                SavedEntriesScreen(returnToPreviousScreen = returnToPreviousScreen)
            }
            composable("signUp") {
                SignUpScreen(
                    returnToPreviousScreen = returnToPreviousScreen,
                    navigateToHomeScreen = toHomeScreen
                )
            }
            composable("welcome") {
                WelcomeScreen(navigateToSignUp = toSignUp, navigateToLogin = toLogin)
            }
            composable("scheduledTours"){
                ScheduledToursScreen(returnToPreviousScreen = returnToPreviousScreen)
            }
            composable("search"){
                SearchScreen(returnToPreviousScreen = returnToPreviousScreen)
            }
            composable("advertise"){
                AdvertiseScreen(returnToPreviousScreen = returnToPreviousScreen)
            }

        }
    }
}