package com.blackbyte.skucise.screens

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.compose.rememberImagePainter
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.AmenityChip
import com.blackbyte.skucise.components.Pager
import com.blackbyte.skucise.components.RatingStars
import com.blackbyte.skucise.data.Amenity
import com.blackbyte.skucise.data.RealtyAdInfo
import com.blackbyte.skucise.data.resolveAmenity
import com.blackbyte.skucise.ui.theme.Cyan
import com.blackbyte.skucise.ui.theme.Gold
import com.blackbyte.skucise.ui.theme.LightGrey
import com.blackbyte.skucise.utils.Config
import com.blackbyte.skucise.utils.Utils

private val _data = MutableLiveData<RealtyAdInfo>()

fun realtyAdInfoInit(t: RealtyAdInfo) {
    _data?.postValue(t)
}

@Composable
fun PropertyEntryScreen(
    dataLive: LiveData<RealtyAdInfo> = _data,
    returnToPreviousScreen: () -> Unit,
    navigateToVendorInbox: (Int) -> Unit,
    navigateToPropertyReviews: (Int) -> Unit,
    navigateToScheduleATour: () -> Unit
) {
    val data: RealtyAdInfo? by dataLive.observeAsState()
    var isFavorite: MutableState<Boolean?> = remember { mutableStateOf(null) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 5.dp,
                modifier = Modifier
                    .height(84.dp)
                    .shadow(
                        shape = RectangleShape,
                        elevation = 10.dp,
                        clip = true
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Button(
                        elevation = ButtonDefaults.elevation(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        onClick = {
                            returnToPreviousScreen()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "arrow back",
                            Modifier.size(size = 32.dp)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start,

                        ) {
                        data?.let {
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.h5.copy(
                                    color = MaterialTheme.colors.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(Modifier.height(5.dp))
                        Row {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "map pin location"
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            data?.let {
                                Text(
                                    text = it.address,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            data?.let {
                Pager(
                    items = it.images,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp),
                    overshootFraction = .75f,
                    contentFactory = { item ->
                        Image(
                            painter = rememberImagePainter(item),
                            contentDescription = "property image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                )
            }
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),

                    ) {
                    Column {
                        /*
                        Row {
                            RatingStars(
                                rating = 4.6f,
                                maxRating = 5,
                                starSize = 16.dp,
                                spacing = 4.dp,
                                tint = Gold
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            data?.let {
                                Text("(${it.avgScore})", fontWeight = FontWeight.Medium)
                            }
                        }
                         */
                        Row(modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    data?.let {
                                        navigateToPropertyReviews(it.id)
                                    }
                                }
                            )
                        }) {
                            Text(
                                "Sve recenzije", // it.totalReviews
                                color = MaterialTheme.colors.secondary,
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                Icons.Filled.ArrowForward,
                                contentDescription = "arrow right",
                                tint = MaterialTheme.colors.secondary
                            )
                        }
                    }
                    Row {


                        data?.let {
                            if (isFavorite.value == null) {
                                isFavorite.value = it.isFavorite
                            }
                            OutlinedButton(
                                onClick = {
                                    Utils.addRemoveFavorite(
                                        property_ad_id = it.id,
                                        onFinish = fun(_body: String, responseCode: Int) {
                                            if (responseCode == 200) {
                                                Handler(Looper.getMainLooper()).post {
                                                    isFavorite.value = !(isFavorite.value!!)
                                                }
                                            }
                                        })
                                },
                                modifier = Modifier
                                    .size(56.dp)
                            ) {
                                Icon(
                                    imageVector = if (isFavorite.value!!) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "add to favorites"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "add to favorites"
                            )
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        data?.let {
                            Text(text = "${if (it.monthly) "Mesečno" else "Prodajna cena"}*")
                            Text(
                                text = "${it.priceRange} ${Config.CURRENCY}",
                                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.size(8.dp))
                    data?.let {
                        Row {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Soba")
                                Text(
                                    "${it.roomsRange}",
                                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                            Spacer(modifier = Modifier.size(48.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Kupatila")
                                Text(
                                    "${it.bathroomRange}",
                                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }

                Text(
                    "*moguća promena cene po dogovoru.",
                    style = MaterialTheme.typography.body2
                )

                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "Raspored soba i cenovnik",
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                )

                data?.let {
                    for (floor in it.floors) {
                        Column(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.onBackground,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(12.dp)
                        )
                        {
                            Text(
                                text = "Soba: ${floor.rooms}, Kupatila: ${floor.bathrooms}".uppercase(),
                                fontWeight = FontWeight.Medium
                            )
                            Divider()
                            Spacer(
                                modifier = Modifier
                                    .size(8.dp)
                            )
                            Row {
                                Box(contentAlignment = Alignment.Center) {
                                    Image(
                                        painter = rememberImagePainter(floor.floorPlanUrl),
                                        contentDescription = "floor plan",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize(0.4f)
                                            .clip(shape = RoundedCornerShape(4.dp))
                                            .border(
                                                2.dp,
                                                color = LightGrey,
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                    )
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = "floor plan zoom in",
                                        tint = Color(0xAA000000),
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                                Spacer(
                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                                Column {
                                    Text("Površina: ${floor.surface}")
                                    if (!it.unified) {
                                        Text("Cena: ${floor.price} ${Config.CURRENCY}")
                                        /*
                                        //SPANSTYLE THROWS EXCEPTION
                                        Text(
                                            text = buildAnnotatedString {
                                                append("Površina: 32 m")
                                                withStyle(
                                                    SpanStyle(
                                                        fontSize = TextStyle.Default.fontSize.div(2),
                                                        baselineShift = BaselineShift.Superscript
                                                    )
                                                ) {
                                                    append("2")
                                                }
                                            }
                                        )*/
                                        if (it.monthly) {
                                            Text("Depozit: ${floor.deposit} ${Config.CURRENCY}")
                                        }
                                    }
                                }
                            }
                            Spacer(
                                modifier = Modifier
                                    .size(12.dp)
                            )
                            Button(
                                onClick = {
                                    data?.let {
                                        scheduleInvokeInit(
                                            listOf<Any>(
                                                it.images[0],
                                                it.title,
                                                it.homeownerName,
                                                it.id,
                                            )
                                        )
                                        navigateToScheduleATour()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Zakažite obilazak")
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.size(12.dp))
                Row {
                    Text(
                        text = "Više o ", style = MaterialTheme.typography.h5.copy(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    data?.let {
                        Text(
                            text = it.title, style = MaterialTheme.typography.h5.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                data?.let {
                    Text(it.description)
                }

                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "Pogodnosti", style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
                data?.let {
                    val amenities: List<Amenity> = it.amenities.map { q -> resolveAmenity(q) }

                    val columns = 2
                    val wholeRows = (amenities.lastIndex + 1) / columns
                    val remainder = (amenities.lastIndex + 1) - (wholeRows * columns)
                    Column {
                        var i = 0
                        while (i < wholeRows) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            ) {
                                for (t in 0 until columns) {
                                    AmenityChip(amenity = amenities[i * columns + t])
                                }
                            }
                            i++
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            var j = 0
                            while (j < remainder) {
                                AmenityChip(amenity = amenities[i * columns + j])
                                j++
                                i++
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "Kontakt", style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.size(4.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    data?.let {
                        Row {
                            Image(
                                painter = rememberImagePainter(it.homeownerUrl),
                                contentDescription = "vendor profile picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(84.dp)
                                    .clip(RoundedCornerShape(42.dp))
                                    .border(
                                        width = 4.dp,
                                        color = LightGrey,
                                        shape = RoundedCornerShape(42.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Column {
                                Text(text = it.homeownerName, fontWeight = FontWeight.Bold)
                                if (!it.homeownerIsNaturalPerson) {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "location icon"
                                        )
                                        Column {
                                            it.addressOfIncorporation?.let { r -> Text(r) }
                                        }
                                    }
                                }
                                Row {
                                    Icon(
                                        imageVector = if (it.homeownerIsNaturalPerson) Icons.Default.Phone else Icons.Default.Home,
                                        contentDescription = "icon"
                                    )
                                    Text(
                                        it.contact,
                                        color = Cyan,
                                        textDecoration = TextDecoration.Underline
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))

                OutlinedButton(
                    onClick = { data?.let {
                        navigateToVendorInbox(it.homeOwnerUserId)
                    } },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pošaljite poruku")
                }
                /* CONTENT GOES ABOVE */
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun PropertyEntryPreview() {
    SkuciSeTheme {
        PropertyEntryScreen({}, {}, {}, {})
    }
}

 */