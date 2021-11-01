package com.blackbyte.skucise.screens

import androidx.compose.foundation.shape.CircleShape
import android.inputmethodservice.Keyboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.components.RatingStars
import com.blackbyte.skucise.ui.theme.Gold
import com.blackbyte.skucise.ui.theme.LightGrey
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

@Composable
fun ReviewsScreen(returnToPreviousScreen: () -> Unit) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = { NavTopBar(title = "Recenzije", returnToPreviousScreen = returnToPreviousScreen) }
    ) {
        val groupedByRating = listOf(
            Triple(5, 9, Color(0xFF33AE08)),
            Triple(4, 4, Color(0xFF83AE08)),
            Triple(3, 2, Color(0xFFD7C205)),
            Triple(2, 1, Color(0xFFEA7E00)),
            Triple(1, 1, Color(0xFFDC3535)),
        )
        var totalRatings = 0
        var avg = 0.0f
        groupedByRating.forEach() {
            totalRatings += it.second
            avg += (it.first * it.second)
        }

        avg /= (totalRatings * 5)
        avg *= 5.0f

        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .height(IntrinsicSize.Min)
            ) {
                Image(
                    painter = painterResource(R.drawable.property_2),
                    contentDescription = "property image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(Color.Transparent, Color.Black),
                                800f,
                                0f,
                            )
                        )
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(vertical = 14.dp, horizontal = 20.dp)
                    ) {
                        Text(
                            text = "${String.format("%.1f", avg)}",
                            style = MaterialTheme.typography.h2.copy(
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.size(18.dp))
                        RatingStars(
                            rating = avg,
                            maxRating = 5,
                            starSize = 22.dp,
                            spacing = 8.dp,
                            tint = Gold
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = "Ukupno ocena: $totalRatings", color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.size(18.dp))
            Text(
                text = "Apartmani Petrović",
                style = MaterialTheme.typography.h5.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.size(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                groupedByRating.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.width(38.dp)) {
                            Text(
                                "${it.first}",
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "star rating",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .weight(1.0f)
                                .background(color = LightGrey)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(it.second.toFloat() / totalRatings)
                                    .background(it.third)
                                    .fillMaxHeight()
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            Surface(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            Image(
                                painter = painterResource(R.drawable.review_profile_pic),
                                contentDescription = "review profile picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Column {
                                Text(
                                    text = "Stefan Đorđević",
                                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                RatingStars(
                                    rating = 4.2f,
                                    maxRating = 5,
                                    starSize = 18.dp,
                                    spacing = 3.dp,
                                    tint = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                        Text("Pre 2 dan/a")
                    }
                    Text("Soba 12 | Period: 6 meseci", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "Po mom useljenju bojler nije radio, međutim stanodavac je brzo rešio problem i to o sopstvenom trošku. Jedina zamerka je što su susedi nemarni i bučni.\n" +
                                "\n" +
                                "U svakom slučaju, sve preporuke!"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewsScreenPreview() {
    SkuciSeTheme {
        ReviewsScreen({})
    }
}