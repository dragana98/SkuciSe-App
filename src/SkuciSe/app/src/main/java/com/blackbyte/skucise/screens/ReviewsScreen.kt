package com.blackbyte.skucise.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.compose.rememberImagePainter
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.components.RatingStars
import com.blackbyte.skucise.data.Review
import com.blackbyte.skucise.ui.theme.Gold
import com.blackbyte.skucise.ui.theme.LightGrey

private val _reviews = MutableLiveData<List<Review>>()

private val _data = MutableLiveData<List<String>>()

fun reviewsInvokeInit(t: List<Review>) {
    _reviews.postValue(t)
}

fun reviewsLoadOtherData(
    t: List<String>
) {
   _data.postValue(t)
}

@Composable
fun ReviewsScreen(
    reviewLive: LiveData<List<Review>> = _reviews,
    dataLive: LiveData<List<String>> = _data,
    returnToPreviousScreen: () -> Unit
) {
    val reviews: List<Review>? by reviewLive.observeAsState()
    val data: List<String>? by dataLive.observeAsState()
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = { NavTopBar(title = "Recenzije", returnToPreviousScreen = returnToPreviousScreen) }
    ) {


        reviews?.let {
            val t: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0)
            for (review in it) {
                t[review.stars - 1]++
            }

            val groupedByRating = listOf(
                Triple(5, t[4], Color(0xFF33AE08)),
                Triple(4, t[3], Color(0xFF83AE08)),
                Triple(3, t[2], Color(0xFFD7C205)),
                Triple(2, t[1], Color(0xFFEA7E00)),
                Triple(1, t[0], Color(0xFFDC3535)),
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
                    data?.let {
                        Image(
                            painter = rememberImagePainter(data!![1]),
                            contentDescription = "property image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
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
                data?.let {
                    Text(
                        text = data!![0],
                        style = MaterialTheme.typography.h5.copy(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
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
                                modifier = Modifier.width(38.dp)
                            ) {
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
                for (review in it) {
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
                                        painter = rememberImagePainter(review.reviewerProfileUrl),
                                        contentDescription = "review profile picture",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(52.dp)
                                            .clip(shape = CircleShape)
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Column {
                                        Text(
                                            text = review.reviewerName,
                                            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        RatingStars(
                                            rating = review.stars * 1.0f,
                                            maxRating = 5,
                                            starSize = 18.dp,
                                            spacing = 3.dp,
                                            tint = MaterialTheme.colors.onSurface
                                        )
                                    }
                                }
                                Text("${review.date.dayOfMonth}.${review.date.monthValue}.${review.date.year}.")
                            }
                            Text(review.contents)
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
fun ReviewsScreenPreview() {
    SkuciSeTheme {
        ReviewsScreen({})
    }
}
 */