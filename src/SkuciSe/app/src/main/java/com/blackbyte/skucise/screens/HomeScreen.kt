package com.blackbyte.skucise.screens

import Card
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.Pager
import com.blackbyte.skucise.data.DrawerEntry
import com.blackbyte.skucise.ui.theme.LightGreen
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.utils.Config
import com.blackbyte.skucise.utils.Utils
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import org.json.JSONArray

import android.widget.Toast
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private val _cards = MutableLiveData<List<Card>>()

fun cardsInvokeInit(t: List<Card>) {
    _cards.postValue(t)
}

@Composable
fun HomeScreen(
    cardLive: LiveData<List<Card>> = _cards,
    drawerOptions: List<DrawerEntry>,
    returnToPreviousScreen: () -> Unit,
    navigateToPropertyEntry: (Int) -> Unit,
    navigateToSavedEntries: () -> Unit,
    navigateToScheduledTours: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToAdvertise: () -> Unit,
) {
    val gradient = Brush.linearGradient(0f to Color.Magenta, 1000f to Color.Yellow)
    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    var enableDrawerGestures by remember { mutableStateOf(true) }
    val regulateDrawer = fun(shouldEnable: Boolean) {
        enableDrawerGestures = shouldEnable
    }

    /* DATA */
    var name by remember { mutableStateOf("") }
    var avatarURL: MutableState<String?> = remember { mutableStateOf(null) }
    val cards: List<Card>? by cardLive.observeAsState()

    Utils.getUserData(onFinish = fun(body: String, responseCode: Int) {
        try {
            val jsonObject = JSONObject(body)
            val _name = jsonObject.getString("name")
            val _surname = jsonObject.getString("surname")
            Handler(Looper.getMainLooper()).post(Runnable {
                avatarURL.value = jsonObject.getString("avatar_url")
                name = "$_name $_surname"
            })
        } catch (e: Exception) {
            Log.d("BODY", "$body\n\n${e.stackTraceToString()}")
        }
    })
    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        scaffoldState = state,
        drawerGesturesEnabled = enableDrawerGestures,
        drawerContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(all = 20.dp)
                    .fillMaxWidth()
            ) {
                if (avatarURL != null) {
                    Image(
                        painter = rememberImagePainter(avatarURL),
                        contentDescription = "review profile picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(84.dp)
                            .clip(shape = CircleShape)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "registration icon",
                        modifier = Modifier.size(84.dp)
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    name,
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Medium)
                )
            }
            Divider()
            // Drawer items
            Spacer(modifier = Modifier.height(12.dp))
            drawerOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = option.icon,
                        contentDescription = "",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = option.label,
                        fontSize = 18.sp,
                        modifier = Modifier.clickable(
                            enabled = true,
                            role = Role.Button
                        ) {
                            if (option.label == "Sačuvani oglasi")
                                navigateToSavedEntries()
                            if (option.label == "Zakazani obilasci")
                                navigateToScheduledTours()
                            if (option.label == "Oglasi")
                                navigateToAdvertise()
                        }
                    )
                }
            }
        },
        topBar = {
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .background(brush = gradient, alpha = 0.5f)
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logomark_black),
                            contentScale = ContentScale.Inside,
                            contentDescription = "Skuci Se icon",
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            "Skući Se",
                            style = MaterialTheme.typography.h5.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                    }

                    Spacer(modifier = Modifier.size(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            contentPadding = PaddingValues(all = 8.dp),
                            modifier = Modifier
                                .size(48.dp),
                            onClick = {
                                scope.launch { if (state.drawerState.isClosed) state.drawerState.open() else state.drawerState.close() }
                            }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "menu icon",
                                tint = Color.Black,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        TextField(
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                cursorColor = Color.Black,
                                disabledLabelColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            value = query,
                            onValueChange = { navigateToSearch() }, //onValueChange = { query = it },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "menu icon",
                                    tint = Color.Black
                                )
                            },
                            label = {
                                Text(text = "Pretraga...",
                                modifier = Modifier.clickable(
                                    enabled = true,
                                    role = Role.Switch
                                ){
                                    navigateToSearch()
                                })
                            },
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .height(48.dp)
                                .padding(0.dp)
                                .fillMaxWidth()

                        )
                    }
                }
            }
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text(
                text = "Aktuelne ponude",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
            )
            cards?.forEach { card ->
                val cardShape = RoundedCornerShape(8.dp)
                Surface(
                    color = MaterialTheme.colors.background,
                    shape = cardShape,
                    elevation = 10.dp,
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                card?.let {
                                    navigateToPropertyEntry(card.id)
                                }
                            }
                        )
                    }
                )
                {
                    Column {
                        Pager(
                            items = card.images,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(256.dp),
                            overshootFraction = .75f,
                            contentFactory = { item ->
                                Image(
                                    painter = rememberImagePainter(item),
                                    contentDescription = "property image",
                                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                                    modifier = Modifier
                                        .fillMaxSize() // add a border (optional)
                                )
                            },
                            regulateDrawer = regulateDrawer
                        )
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = card.title,
                                style = MaterialTheme.typography.h5.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.primary
                                )
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            val ribbonShape = RoundedCornerShape(0.dp, 18.dp, 18.dp, 0.dp)

                            Box(modifier = Modifier.offset(x = (-12).dp, y = 0.dp)) {
                                Column(
                                    modifier = Modifier
                                        .clip(shape = ribbonShape)
                                        .background(color = LightGreen, shape = ribbonShape)
                                ) {
                                    Text(
                                        "${card.priceRange} ${Config.CURRENCY}${if (card.monthly) ", mesečno" else ""}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(
                                            12.dp,
                                            4.dp,
                                            20.dp,
                                            4.dp
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(12.dp))
                            Column {
                                Row {
                                    Text(text = "Broj soba: ", fontWeight = FontWeight.Bold)
                                    Text(text = card.roomCount)
                                }
                                Row {
                                    Text(text = "Grad: ", fontWeight = FontWeight.Bold)
                                    Text(text = card.city)
                                }
                            }

                            Spacer(modifier = Modifier.size(12.dp))

                            val amenities = card.amenities
                            val pillShape = RoundedCornerShape(18.dp)

                            Row {
                                LazyRow {
                                    items(amenities.lastIndex + 1) {
                                        Box(
                                            modifier = Modifier
                                                .clip(shape = pillShape)
                                                .background(
                                                    color = MaterialTheme.colors.onBackground,
                                                    shape = ribbonShape
                                                )
                                        ) {
                                            Text(
                                                text = amenities[it],
                                                color = MaterialTheme.colors.background,
                                                modifier = Modifier.padding(
                                                    horizontal = 20.dp,
                                                    vertical = 4.dp
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(8.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SkuciSeTheme {
        HomeScreen(listOf(), {}, {}, {}, {}, {}, {})
    }
}
 */