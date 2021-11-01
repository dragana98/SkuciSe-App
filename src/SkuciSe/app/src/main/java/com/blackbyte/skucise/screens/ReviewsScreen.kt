package com.blackbyte.skucise.screens

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.components.RatingStars
import com.blackbyte.skucise.ui.theme.Gold
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

@Composable
fun ReviewsScreen(returnToPreviousScreen: () -> Unit) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = { NavTopBar(title = "Recenzije", returnToPreviousScreen = returnToPreviousScreen) }
    ) {
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
                                1000f,
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
                            text = "4.8",
                            style = MaterialTheme.typography.h2.copy(
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.size(18.dp))
                        RatingStars(
                            rating = 4.6f,
                            maxRating = 5,
                            starSize = 22.dp,
                            spacing = 8.dp,
                            tint = Gold
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = "Ukupno ocena: 9", color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "Apartmani Petrović",
                style = MaterialTheme.typography.h5.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
            )
            Column {

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