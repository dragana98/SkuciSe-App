package com.blackbyte.skucise

import Card
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blackbyte.skucise.data.DrawerEntry
import com.blackbyte.skucise.data.Floor
import com.blackbyte.skucise.data.RealtyAdInfo
import com.blackbyte.skucise.screens.*
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.utils.Prefs
import com.blackbyte.skucise.utils.Utils
import org.json.JSONObject

val prefs: Prefs by lazy {
    MainActivity.prefs!!
}


class MainActivity : ComponentActivity() {
    companion object {
        var prefs: Prefs? = null
        lateinit var instance: MainActivity
            private set
    }

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        prefs = Prefs(applicationContext)

        /*
        val authTokenVal = prefs.authToken // GET
        prefs.authToken = 9  // SET
        */

        setContent {
            SkuciSeTheme {
                val navController = rememberNavController()
                AppNavigator(navController = navController)
            }
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    fun AppNavigator(navController: NavHostController) {
        val returnToPreviousScreen = fun() {
            navController.popBackStack()
        }

        val toHomeScreen = fun() {
            navController.navigate(route = "home") {
                launchSingleTop = true
            }
        }
        val toInbox = fun() {
            navController.navigate(route = "inbox") {
                launchSingleTop = true
            }
        }
        val toLogin = fun() {
            navController.navigate(route = "login") {
                launchSingleTop = true
            }
        }
        val toMessages = fun() {
            navController.navigate(route = "messages") {
                launchSingleTop = true
            }
        }
        val toMyAccount = fun() {
            navController.navigate(route = "myAccount") {
                launchSingleTop = true
            }
        }
        val toPropertyEntry = fun(id: Int) {
            Utils.getPropertyById(id = id, onFinish = fun(_body: String, responseCode: Int) {
                if (responseCode == 200) {
                    var body = _body
                    val jsonObj = JSONObject(body)
                    val id = jsonObj.getInt("id")
                    val realtorId = jsonObj.getInt("realtor_id")
                    val postalCode = jsonObj.getInt("postal_code")
                    val title = jsonObj.getString("name")
                    val city = jsonObj.getString("city")
                    val description = jsonObj.getString("description")
                    val isFavorite = jsonObj.getBoolean("is_favorite")
                    val amenities = mutableListOf<String>()
                    val images = mutableListOf<String>()
                    var priceRange = ""
                    var deposit: Int? = null
                    var bathroomRange = ""
                    var roomsRange = ""
                    var monthly = false
                    var unified = false

                    val _amenities = jsonObj.getJSONArray("amenities")
                    val _images = jsonObj.getJSONArray("images")
                    val _leasable = jsonObj.getInt("leasable")
                    val _unified = jsonObj.getInt("unified")

                    for (p in 0 until _amenities.length()) {
                        amenities.add(_amenities[p] as String)
                    }
                    for (p in 0 until _images.length()) {
                        images.add(_images[p] as String)
                    }

                    var propertyAdRealties = jsonObj.getJSONArray("property_ad_realties")
                    var adRealtiesPrices = mutableListOf<Int>()
                    var adRealtiesBathrooms = mutableListOf<Int>()
                    var adRealtiesNumberOfRooms = mutableListOf<Int>()

                    var _floors = mutableListOf<Floor>()

                    if (_unified != 0) {
                        priceRange = "${jsonObj.getInt("price")}"
                        deposit = jsonObj.getInt("deposit")
                    } else {
                        for (p in 0 until propertyAdRealties.length()) {
                            val propertyObject = (propertyAdRealties[p] as JSONObject)
                            adRealtiesPrices.add(propertyObject.getInt("price"))
                        }
                        priceRange =
                            "${adRealtiesPrices.minOrNull()} - ${adRealtiesPrices.maxOrNull()}"
                    }

                    for (p in 0 until propertyAdRealties.length()) {
                        val propertyObject = (propertyAdRealties[p] as JSONObject)
                        adRealtiesBathrooms.add(propertyObject.getInt("number_of_bathrooms"))
                        adRealtiesNumberOfRooms.add(propertyObject.getInt("number_of_rooms"))
                        _floors.add(
                            Floor(
                                id = propertyObject.getInt("id"),
                                rooms = propertyObject.getInt("number_of_rooms"),
                                bathrooms = propertyObject.getInt("number_of_bathrooms"),
                                surface = propertyObject.getInt("surface"),
                                floorPlanUrl = propertyObject.getString("floor_plan_url"),
                                price = if (_unified != 0) null else propertyObject.getInt("price"),
                                deposit = if (_leasable != 0) propertyObject.getInt("deposit") else null
                            )
                        )
                    }
                    bathroomRange =
                        "${adRealtiesBathrooms.minOrNull()} - ${adRealtiesBathrooms.maxOrNull()}"
                    roomsRange =
                        "${adRealtiesNumberOfRooms.minOrNull()} - ${adRealtiesNumberOfRooms.maxOrNull()}"

                    if (_leasable != 0) {
                        monthly = true
                    }

                    if (_unified != 0) {
                        unified = true
                    }

                    Handler(Looper.getMainLooper()).post {
                        realtyAdInfoInit(
                            RealtyAdInfo(
                                id = id,
                                title = title,
                                address = "$postalCode, $city",
                                description = description,
                                isFavorite = isFavorite,
                                amenities = amenities,
                                images = images,
                                monthly = monthly,
                                unified = unified,
                                priceRange = priceRange,
                                deposit = deposit,
                                roomsRange = roomsRange,
                                bathroomRange = bathroomRange,
                                floors = _floors,
                                totalReviews = 5,
                                avgScore = 4.2
                            )
                        )
                    }
                } else {
                    Handler(Looper.getMainLooper()).post(Runnable {
                        Log.d(
                            "ERROR REQUEST UNSUCCESSFUL",
                            "Response code $responseCode Body $_body"
                        )
                    })
                }

            })

            navController.navigate(route = "propertyEntry") {
                launchSingleTop = true
            }
        }
        val toReviews = fun() {
            navController.navigate(route = "reviews") {
                launchSingleTop = true
            }
        }
        val toSavedEntries = fun() {
            navController.navigate(route = "savedEntries") {
                launchSingleTop = true
            }
        }
        val toSignUp = fun() {
            navController.navigate(route = "signUp") {
                launchSingleTop = true
            }
        }
        val toWelcome = fun() {
            navController.navigate(route = "welcome") {
                launchSingleTop = true
            }
        }
        var toScheduledTours = fun() {
            navController.navigate(route = "scheduledTours") {
                launchSingleTop = true
            }
        }
        var toScheduleATour = fun() {
            navController.navigate(route = "scheduleATour") {
                launchSingleTop = true
            }
        }
        var toSearch = fun() {
            navController.navigate(route = "search") {
                launchSingleTop = true
            }
        }
        var toAdvertise = fun() {
            navController.navigate(route = "advertise") {
                launchSingleTop = true
            }
        }

        val authTokenVal = prefs?.authToken
        // TODO test if token expired
        var dest = "welcome"
        if (authTokenVal != "unset") {
            dest = "home"
        }

        NavHost(navController = navController, startDestination = dest) {
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
                Utils.getAllPropertyAds(onFinish = fun(_body: String, responseCode: Int) {
                    if (responseCode == 200) {
                        var __cards = mutableListOf<Card>()
                        var body = "{\"data\": $_body}"
                        val jsonObj = JSONObject(body)

                        val dataArray = jsonObj.getJSONArray("data")
                        val length = dataArray.length()
//loop to get all json objects from data json array
                        for (i in 0 until length) {
                            val arrayObject = dataArray.getJSONObject(i)
                            val id = arrayObject.getInt("id")
                            val title = arrayObject.getString("name")
                            val city = arrayObject.getString("city")
                            val _amenities = arrayObject.getJSONArray("amenities")
                            val _images = arrayObject.getJSONArray("images")
                            val _leasable = arrayObject.getInt("leasable")
                            val _unified = arrayObject.getInt("unified")
                            val amenities = mutableListOf<String>()
                            val images = mutableListOf<String>()
                            var priceRange = ""
                            var roomCount = ""
                            var monthly = false

                            for (p in 0 until _amenities.length()) {
                                amenities.add(_amenities[p] as String)
                            }
                            for (p in 0 until _images.length()) {
                                images.add(_images[p] as String)
                            }

                            var propertyAdRealties =
                                arrayObject.getJSONArray("property_ad_realties")
                            var adRealtiesPrices = mutableListOf<Int>()
                            var adRealtiesNumberOfRooms = mutableListOf<Int>()

                            if (_unified != 0) {
                                priceRange = "${arrayObject.getInt("price")}"
                            } else {

                                for (p in 0 until propertyAdRealties.length()) {
                                    val propertyObject = (propertyAdRealties[p] as JSONObject)
                                    adRealtiesPrices.add(propertyObject.getInt("price"))
                                }
                                priceRange =
                                    "${adRealtiesPrices.minOrNull()} - ${adRealtiesPrices.maxOrNull()}"
                            }

                            for (p in 0 until propertyAdRealties.length()) {
                                val propertyObject = (propertyAdRealties[p] as JSONObject)
                                adRealtiesNumberOfRooms.add(propertyObject.getInt("number_of_rooms"))
                            }

                            roomCount =
                                "${adRealtiesNumberOfRooms.minOrNull()} - ${adRealtiesNumberOfRooms.maxOrNull()}"

                            if (_leasable != 0) {
                                monthly = true
                            }

                            __cards.add(
                                Card(
                                    id,
                                    title,
                                    priceRange,
                                    monthly,
                                    roomCount,
                                    city,
                                    amenities,
                                    images
                                )
                            )
                            /*
                            val len = images.length()
                            val Ingredients_names: ArrayList<String> = ArrayList()
                            for (j in 0 until len) {
                                val json = images.getString
                                Ingredients_names.add(json.getString("name"))
                            }*/
                        }
                        Handler(Looper.getMainLooper()).post {
                            cardsInvokeInit(__cards)
                        }
                    } else {
                        Handler(Looper.getMainLooper()).post(Runnable {
                            Log.d(
                                "ERROR REQUEST UNSUCCESSFULL",
                                "Response code $responseCode Body $_body"
                            )
                        })
                    }

                })

                HomeScreen(
                    drawerOptions = listOf(
                        DrawerEntry(
                            label = "Moj nalog",
                            icon = Icons.Filled.AccountCircle,
                            onTap = { toMyAccount() }
                        ),
                        DrawerEntry(
                            label = "Oglasi",
                            icon = Icons.Filled.House,
                            onTap = { toAdvertise() }
                        ),
                        DrawerEntry(
                            label = "Sačuvani oglasi",
                            icon = Icons.Filled.Favorite,
                            onTap = { toSavedEntries() }
                        ),
                        DrawerEntry(
                            label = "Poruke",
                            icon = Icons.Filled.Email,
                            onTap = { toMessages() }
                        ),
                        DrawerEntry(
                            label = "Zakazani obilasci",
                            icon = Icons.Filled.DateRange,
                            onTap = { toScheduledTours() }
                        ),
                        DrawerEntry(
                            label = "Podešavanja",
                            icon = Icons.Filled.Settings,
                            onTap = { /* TODO */ }
                        ),
                        DrawerEntry(
                            label = "Odjava",
                            icon = Icons.Filled.ExitToApp,
                            onTap = {
                                prefs?.authToken = "unset"
                                prefs?.authToken = "unset"

                                toWelcome()
                            }
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
                    navigateToHomeScreen = toHomeScreen,
                    navigateToSignUpScreen = toSignUp
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

                PropertyEntryScreen(
                    navigateToPropertyReviews = toReviews,
                    navigateToVendorInbox = toInbox,
                    navigateToScheduleATour = toScheduleATour,
                    returnToPreviousScreen = returnToPreviousScreen
                )
            }
            composable("reviews") {
                ReviewsScreen(returnToPreviousScreen = returnToPreviousScreen)
            }
            composable("savedEntries") {
                SavedEntriesScreen(returnToPreviousScreen = returnToPreviousScreen)
            }
            composable("signUp") {
                SignUpScreen(
                    returnToPreviousScreen = returnToPreviousScreen,
                    navigateToLoginScreen = toLogin
                )
            }
            composable("welcome") {
                WelcomeScreen(navigateToSignUp = toSignUp, navigateToLogin = toLogin)
            }
            composable("scheduledTours") {
                ScheduledToursScreen(
                    returnToPreviousScreen = returnToPreviousScreen
                )
            }
            composable("scheduleATour") {
                ScheduleATourScreen(
                    returnToPreviousScreen = returnToPreviousScreen
                )
            }
            composable("search") {
                SearchScreen(returnToPreviousScreen = returnToPreviousScreen)
            }
            composable("advertise") {
                AdvertiseScreen(returnToPreviousScreen = returnToPreviousScreen)
            }

        }
    }
}