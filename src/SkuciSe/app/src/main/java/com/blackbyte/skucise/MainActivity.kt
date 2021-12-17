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
import com.blackbyte.skucise.components.Chat
import com.blackbyte.skucise.components.InboxMessage
import com.blackbyte.skucise.components.MsgType
import com.blackbyte.skucise.data.*
import com.blackbyte.skucise.screens.*
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.utils.Prefs
import com.blackbyte.skucise.utils.Utils
import org.json.JSONObject
import org.json.JSONTokener
import java.time.LocalDate

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
        var returnToPreviousScreen = fun() {
            navController.popBackStack()
        }

        var toHomeScreen = fun() {
            navController.navigate(route = "home") {

            }
        }
        val toInbox = fun(id: Int) {
            inboxScreenPrepare(id = id)
            navController.navigate(route = "inbox") {

            }
        }
        var toLogin = fun() {
            navController.navigate(route = "login") {

            }
        }
        val toMessages = fun() {
            prepareMessagePreviews()
            navController.navigate(route = "messages") {

            }
        }

        val toMyAccount = fun() {
            Utils.getUserData(
                onFinish = fun(body: String, responseCode: Int) {
                    if (responseCode == 200) {
                        val jsonObj =
                            JSONTokener(body).nextValue() as JSONObject
                        val name = jsonObj.getString("name")
                        val surname = jsonObj.getString("surname")
                        val contact = jsonObj.getString("phone_number")
                        val url = jsonObj.getString("avatar_url")
                        val email = jsonObj.getString("username")
                        Handler(Looper.getMainLooper()).post(Runnable {
                            loadMyAccountData(listOf(name, surname, url, contact, email))
                            Log.d("PREFETCHED DATA", listOf(name, surname, url, contact, email).toString())
                        })
                    } else {
                        Log.d("REQUEST ERROR", body)
                    }
                })
            navController.navigate(route = "myAccount") {

            }
        }
        val toPropertyEntry = fun(id: Int) {
            Utils.getPropertyById(id = id, onFinish = fun(_body: String, responseCode: Int) {
                if (responseCode == 200) {
                    var body = _body
                    val jsonObj = JSONObject(body)
                    val __id = id
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
                                deposit = if ((_leasable != 0) && (_unified == 0)) propertyObject.getInt(
                                    "deposit"
                                ) else null
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


                    Utils.getRealtorData(
                        realtor_id = realtorId,
                        onFinish = fun(_body: String, responseCode: Int) {
                            if (responseCode == 200) {
                                var body = _body
                                val jsonObj = JSONObject(body)
                                val id = jsonObj.getInt("id")
                                val userId = jsonObj.getInt("user_id")
                                val naturalPerson = jsonObj.getInt("natural_person")

                                var homeownerIsNaturalPerson =
                                    if (naturalPerson != 0) true else false
                                var homeownerUrl: String = ""
                                var homeownerName: String = ""
                                var contact: String = ""
                                var addressOfIncorporation: String? = null


                                var build = fun() {
                                    Handler(Looper.getMainLooper()).post {
                                        realtyAdInfoInit(
                                            RealtyAdInfo(
                                                id = __id,
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
                                                avgScore = 4.2,
                                                addressOfIncorporation = addressOfIncorporation,
                                                contact = contact,
                                                homeOwnerUserId = userId,
                                                homeownerIsNaturalPerson = homeownerIsNaturalPerson,
                                                homeownerName = homeownerName,
                                                homeownerUrl = homeownerUrl
                                            )
                                        )
                                    }
                                }

                                if (naturalPerson != 0) {
                                    Utils.getBasicUserData(
                                        user_id = userId,
                                        onFinish = fun(body: String, responseCode: Int) {
                                            if (responseCode == 200) {
                                                val jsonObj =
                                                    JSONTokener(body).nextValue() as JSONObject
                                                homeownerName =
                                                    "${jsonObj.getString("name")} ${
                                                        jsonObj.getString(
                                                            "surname"
                                                        )
                                                    }"
                                                contact = jsonObj.getString("phone_number")
                                                homeownerUrl = jsonObj.getString("avatar_url")
                                                build()
                                            } else {
                                                Log.d("REQUEST ERROR", body)
                                            }
                                        })
                                } else {
                                    Utils.getRealtorLegalEntityData(
                                        realtor_id = id,
                                        onFinish = fun(body: String, responseCode: Int) {
                                            Handler(Looper.getMainLooper()).post(Runnable {
                                                Log.d(
                                                    "RESPONSE",
                                                    "$body"
                                                )
                                            })
                                            if (responseCode == 200) {
                                                val jsonObj = JSONObject(body)
                                                val id = jsonObj.getInt("id")
                                                homeownerName = jsonObj.getString("name")
                                                addressOfIncorporation =
                                                    jsonObj.getString("corporate_address")
                                                contact = jsonObj.getString("website_url")
                                                homeownerUrl = jsonObj.getString("avatar_url")
                                                build()
                                            } else {
                                                Log.d("REQUEST ERROR", body)
                                            }
                                        })
                                }
                            }
                        })
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

            }
        }
        val toReviews = fun(id: Int) {
            Utils.getAllReviewsUsersToRealty(
                id = id,
                onFinish = fun(_body: String, responseCode: Int) {
                    Handler(Looper.getMainLooper()).post {
                        Log.d("GET ALL REVIEWS", "$responseCode  {\"data\": $_body}")
                    }
                    if (responseCode == 200) {

                        var _reviews = mutableListOf<Review>()

                        var body = "{\"data\": $_body}"
                        val jsonObj = JSONObject(body)

                        val allData = jsonObj.getJSONArray("data")

                        for (p in 0 until allData.length()) {
                            var rev = allData[p] as JSONObject

                            var _rev_id = rev.getInt("id")

                            Utils.getBasicUserData(
                                user_id = _rev_id,
                                onFinish = fun(body: String, responseCode: Int) {
                                    Handler(Looper.getMainLooper()).post {
                                        Log.d("GET BASIC USER DATA", "$responseCode  $body")
                                    }
                                    if (responseCode == 200) {
                                        val jsonObj = JSONObject(body)
                                        val _reviewerName =
                                            "${jsonObj.getString("name")} ${jsonObj.getString("surname")}"
                                        val _reviewerUrl = jsonObj.getString("avatar_url")
                                        _reviews.add(
                                            Review(
                                                stars = rev.getInt("stars"),
                                                date = LocalDate.parse(
                                                    rev.getString("date")
                                                        .substring(startIndex = 0, endIndex = 10)
                                                ),
                                                contents = rev.getString("contents"),
                                                reviewerName = _reviewerName,
                                                reviewerProfileUrl = _reviewerUrl
                                            )
                                        )
                                        Handler(Looper.getMainLooper()).post({
                                            reviewsInvokeInit(_reviews)
                                        })
                                        Utils.getPropertyById(
                                            id = id,
                                            onFinish = fun(body: String, responseCode: Int) {
                                                if (responseCode == 200) {
                                                    val jsonObj = JSONObject(body)

                                                    val _name = jsonObj.getString("name")
                                                    val _images = jsonObj.getJSONArray("images")
                                                    val _image = _images[0] as String


                                                    Handler(Looper.getMainLooper()).post(Runnable {
                                                        reviewsLoadOtherData(listOf(_name, _image))
                                                    })

                                                }
                                            })
                                    }
                                })
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


            navController.navigate(route = "reviews") {

            }
        }
        val toSavedEntries = fun() {
            Utils.getAllFavorites(onFinish = fun(body: String, responseCode: Int) {
                if (responseCode == 200) {
                    val _entries: MutableList<Ad> = mutableListOf()
                    val jsonObj = JSONObject("{\"data\": $body}")

                    val allObjects = jsonObj.getJSONArray("data")

                    for (q in 0 until allObjects.length()) {
                        val tobj = allObjects[q] as JSONObject
                        val property_id = tobj.getInt("property_ad_id")

                        Utils.getPropertyById(
                            id = property_id,
                            onFinish = fun(body: String, responseCode: Int) {
                                if (responseCode == 200) {
                                    val jsonObj = JSONObject(body)

                                    val _name = jsonObj.getString("name")
                                    val _images = jsonObj.getJSONArray("images")
                                    val _image = _images[0] as String

                                    _entries.add(Ad(_name, _image))
                                }
                            })
                    }

                    Handler(Looper.getMainLooper()).post(Runnable {
                        entriesInvokeInit(_entries)
                    })
                }
            })

            navController.navigate(route = "savedEntries") {

            }
        }
        var toSignUp = fun() {
            navController.navigate(route = "signUp") {

            }
        }
        var toWelcome = fun() {
            navController.navigate(route = "welcome") {

            }
        }
        var toScheduledTours = fun() {
            Utils.getAllTourDatesForUser(onFinish = fun(body: String, responseCode: Int) {
                if (responseCode == 200) {
                    var res = mutableListOf<List<Any>>()
                    val jsonObj = JSONObject("{\"body\": $body}")
                    val jsonArr = jsonObj.getJSONArray("body")

                    for (p in 0 until jsonArr.length()) {
                        val singleObj = jsonArr[p] as JSONObject

                        val date = LocalDate.parse(
                            singleObj.getString("date").substring(startIndex = 0, endIndex = 10)
                        )
                        val id = singleObj.getInt("property_ad_id")

                        Utils.getPropertyById(
                            id = id,
                            onFinish = fun(body: String, responseCode: Int) {
                                if (responseCode == 200) {
                                    val jsonObj = JSONObject(body)

                                    val _name = jsonObj.getString("name")
                                    val _images = jsonObj.getJSONArray("images")
                                    val _street_address = jsonObj.getString("street_address")
                                    val _postalcode = jsonObj.getString("postal_code")
                                    val _city = jsonObj.getString("city")

                                    val _image = _images[0] as String

                                    res.add(
                                        listOf<Any>(
                                            date,
                                            _name,
                                            _image,
                                            "$_street_address, $_city, $_postalcode"
                                        )
                                    )
                                }
                            })

                    }
                    Handler(Looper.getMainLooper()).post {
                        addScheduledEntries(res)
                    }
                }
            })
            navController.navigate(route = "scheduledTours") {

            }
        }
        var toScheduleATour = fun() {
            navController.navigate(route = "scheduleATour") {

            }
        }
        var toSearch = fun() {
            navController.navigate(route = "search") {

            }
        }
        var toAdvertise = fun() {
            navController.navigate(route = "advertise") {

            }
        }
        var toAd = fun() {
            navController.navigate(route = "ad") {
                launchSingleTop = true
            }
        }


        val authTokenVal = prefs?.authToken

        // TODO test if token expired
        var dest = "welcome"
        if (authTokenVal != "unset") {
            dest = "home"
        }

        NavHost(navController = navController, startDestination = dest)
        {
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
                            onTap = { toAd() }
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
                    navigateToSearch = toSearch
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
            composable("ad") {
                AdScreen(
                    returnToPreviousScreen = returnToPreviousScreen,
                    navigateToAdvertise = toAdvertise
                )
            }

        }
    }
}