package com.blackbyte.skucise.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.blackbyte.skucise.ui.theme.Cyan
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.AmenityChip
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.components.Pager
import com.blackbyte.skucise.components.RatingStars
import com.blackbyte.skucise.data.Amenity
import com.blackbyte.skucise.data.PropertyEntry
import com.blackbyte.skucise.ui.theme.Gold
import com.blackbyte.skucise.ui.theme.LightGrey
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import kotlin.math.round

@Composable
fun PropertyEntryScreen(
    returnToPreviousScreen: () -> Unit,
    navigateToVendorInbox: () -> Unit,
    navigateToPropertyReviews: () -> Unit,
    navigateToScheduleATour: () -> Unit
) {
    //val propertyEntry: PropertyEntry(id = 1,
    //    imageUrls = listOf("http://localhost:8080/img/property_1.jpg","http://localhost:8080/img/property_2.jpg"),
    //    propertyName = "Apartmani Petrović",
    //    approximateLocation = "34000, Kragujevac",
    //    flats = listOf(
    //
    //    ),
    //    val addedToFavorites: Boolean,
    //    val avgReviewRating: Float,
    //    val description: String,
    //    val amenities: List<Amenity>,) = object : Leasable()
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
                        Text(
                            "Apartmani Petrović",
                            style = MaterialTheme.typography.h5.copy(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(Modifier.height(5.dp))
                        Row {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "map pin location"
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "34000, Kragujevac",
                                fontWeight = FontWeight.Medium
                            )
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
            Pager(
                items = listOf(
                    R.drawable.property_1,
                    R.drawable.property_2
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp),
                overshootFraction = .75f,
                contentFactory = { item ->
                    Image(
                        painter = painterResource(item),
                        contentDescription = "property image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            )
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),

                    ) {
                    Column {
                        Row {
                            RatingStars(
                                rating = 4.6f,
                                maxRating = 5,
                                starSize = 16.dp,
                                spacing = 4.dp,
                                tint = Gold
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Text("(4.8)", fontWeight = FontWeight.Medium)
                        }
                        Row(modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    navigateToPropertyReviews()
                                }
                            )
                        }) {
                            Text(
                                "Sve recenzije (9)",
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
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "add to favorites"
                            )
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
                        Text("Mesečno*")
                        Text(
                            "200.00 - 600.00 EUR",
                            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.size(8.dp))
                    Row {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Soba")
                            Text(
                                "2 - 4",
                                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        Spacer(modifier = Modifier.size(48.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Kupatila")
                            Text(
                                "1 - 2",
                                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                            )
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
                        text = "Dvosoban stan, jedno kupatilo".toUpperCase(),
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
                                painter = painterResource(id = R.drawable.floor_plan_1),
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
                            Text("200.00 EUR, mesečno")
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
                            Text("Depozit: 300.00 EUR")
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .size(12.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("Slobodnih jedinica: ", fontWeight = FontWeight.Bold)
                        Text("2")
                    }
                    Spacer(
                        modifier = Modifier
                            .size(6.dp)
                    )
                    Button(
                        onClick = { navigateToScheduleATour() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Zakažite oblilazak")
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
                    Text(
                        text = "Apartmani Petrović", style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Text("Dobro nam došli! Apartmani Petrović nalaze se u samom centru grada, tako da Vam je sve nadohvat ruke - šoping, prodavnice, škole, fakulteti.")

                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "Pogodnosti", style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                )

                val amenities: List<Amenity> = listOf(
                    Amenity.TERRACE,
                    Amenity.PET_FRIENDLY,
                    Amenity.FURNISHED,
                    Amenity.TV,
                    Amenity.WIFI
                )
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
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.profile_pic_vendor),
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
                            Text(text = "GHP Management d.o.o.", fontWeight = FontWeight.Bold)
                            Row {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "location icon"
                                )
                                Column {
                                    Text("Radoja Domanovića 12")
                                    Text("Kragujevac 34112")
                                }
                            }
                            Row {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "location icon"
                                )
                                Text(
                                    "www.ghpmana.rs",
                                    color = Cyan,
                                    textDecoration = TextDecoration.Underline
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))

                OutlinedButton(
                    onClick = { navigateToVendorInbox() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pošaljite poruku")
                }
                /* CONTENT GOES ABOVE */
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PropertyEntryPreview() {
    SkuciSeTheme {
        PropertyEntryScreen({}, {}, {}, {})
    }
}
