package com.blackbyte.skucise.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.Ad
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

@Composable
fun SavedEntriesScreen(returnToPreviousScreen: () -> Unit) {
    var entries = mutableListOf<Ad>(
        Ad("Apartmani Petrovic","600.00e","mesecno"),
        Ad("Apartmani Miletic","200.00e - 700.00","mesecno"),
        Ad("Apartmani Zlatibor","20.00e","dan"),
        Ad("Kopaonik Lux","600.00e","mesecno"),
        Ad("Apartmani Petrovic","600.00e","mesecno"),
        Ad("Apartmani Petrovic","600.00e","mesecno"),
        Ad("Apartmani Petrovic","600.00e","mesecno"),
        Ad("Apartmani Petrovic","600.00e","mesecno"),
        Ad("Apartmani Petrovic","600.00e","mesecno")

    )

    Scaffold(
        topBar = { NavTopBar("Sačuvani oglasi", returnToPreviousScreen = returnToPreviousScreen) },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight() // verticalArrangement = Arrangement.Center
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ){
                items(entries.size) { index ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                        //, color = background(Color.LightGray)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        )
                        {
                            Row(
                                modifier = Modifier
                                    //.fillMaxWidth()
                                    //.clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.property_2),
                                    contentScale = ContentScale.Inside,
                                    contentDescription = "Property 2",
                                    modifier = Modifier
                                        .size(width = 120.dp, height = 90.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topStartPercent = 10,
                                                bottomEndPercent = 0,
                                                bottomStartPercent = 10,
                                                topEndPercent = 0
                                            )
                                        )
                                        .border(
                                            1.dp, Color.Gray, // RoundedCornerShape(percent = 10)
                                            RoundedCornerShape(
                                                topStartPercent = 10,
                                                bottomEndPercent = 0,
                                                bottomStartPercent = 10,
                                                topEndPercent = 0
                                            )
                                        )
                                )
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier= Modifier.fillMaxWidth()

                                ){
                                    Text(
                                        text = entries[index].adName,
                                        modifier = Modifier.absolutePadding(5.dp,5.dp,0.dp,0.dp),
                                        color = MaterialTheme.colors.primary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 19.sp,
                                        textAlign = TextAlign.Left
                                    )
                                    Text(
                                        text = entries[index].price + ", " + entries[index].details,  //"200.00 - 600.00e, mesečno",
                                        modifier = Modifier.absolutePadding(5.dp,1.dp,0.dp,0.dp),
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Left
                                    )
                                    // strelica i Detalji
                                    Row(
                                        verticalAlignment = Alignment.Bottom,
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .absolutePadding(0.dp, 3.dp, 10.dp, 0.dp)
                                    ){
                                        Text(
                                            text = "Detalji",
                                            modifier = Modifier.absolutePadding(0.dp,2.dp,0.dp,0.dp),
                                            fontSize = 14.sp,
                                            textAlign = TextAlign.Right,
                                            color = Color.Blue
                                        )
                                        Icon(
                                            imageVector = Icons.Filled.ArrowForward,
                                            contentDescription = "Details",
                                            modifier = Modifier.size(16.dp),
                                            tint = Color.Blue
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(size = 12.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SavedEntriesScreenPreview () {
    SkuciSeTheme {
        SavedEntriesScreen({})
    }
}