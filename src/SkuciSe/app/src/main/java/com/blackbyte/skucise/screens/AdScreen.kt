package com.blackbyte.skucise.screens


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
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
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.data.amenityList
import com.blackbyte.skucise.data.listOfAmenities
import com.blackbyte.skucise.data.listOfObjects
import com.blackbyte.skucise.ui.theme.SkuciSeTheme



@Composable
fun AdScreen(
    returnToPreviousScreen: () -> Unit,
    navigateToAdvertise: () -> Unit
) {
    Scaffold(
        topBar = { NavTopBar("Moji oglasi", returnToPreviousScreen = returnToPreviousScreen) },
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
                BottomNavBarAds(navigateToAdvertise = navigateToAdvertise)
        }
    ) {
        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight() // verticalArrangement = Arrangement.Center
            .verticalScroll(rememberScrollState())
        ) {


            //Spacer(modifier = Modifier.size(20.dp))

            // Moji oglasi
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
                            text = "Apartmani Petrović",
                            modifier = Modifier.absolutePadding(5.dp,5.dp,0.dp,0.dp),
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = "600.00e - 700.00e" + ", " + "mesečno",  //"200.00 - 600.00e, mesečno",
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
            }//

            Spacer(modifier = Modifier.size(20.dp))

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
                            text = "Apartmani Petrović",
                            modifier = Modifier.absolutePadding(5.dp,5.dp,0.dp,0.dp),
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = "200.00e - 300.00e" + ", " + "mesečno",  //"200.00 - 600.00e, mesečno",
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
            }//




        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdScreenPreview () {
    SkuciSeTheme {
        AdScreen({},{})
    }
}
