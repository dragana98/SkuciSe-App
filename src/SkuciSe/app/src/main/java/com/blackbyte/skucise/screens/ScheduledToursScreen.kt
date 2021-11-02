package com.blackbyte.skucise.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material.icons.outlined.LockClock
import androidx.compose.material.icons.outlined.QueryBuilder
import androidx.compose.material.icons.outlined.Watch
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
import com.blackbyte.skucise.components.DatePickerGrid
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.data.Ad
import com.blackbyte.skucise.ui.theme.Cyan
import com.blackbyte.skucise.ui.theme.LightGray
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import java.time.LocalDate

@Composable
fun ScheduledToursScreen(returnToPreviousScreen: () -> Unit){

    Scaffold(
        topBar = { NavTopBar("Zakazani obilasci", returnToPreviousScreen = returnToPreviousScreen) },
        backgroundColor = MaterialTheme.colors.background
    ) {

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ){
            var pickedDate by remember {
                mutableStateOf(LocalDate.now())
            }

            Text(
                text = "Po datumu:",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primary
            )

            Box (
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    DatePickerGrid(
                        date = LocalDate.of(2021, 11, 14),
                        highlighted = listOf(
                            LocalDate.of(2021, 11, 6),
                            LocalDate.of(2021, 11, 7),
                            LocalDate.of(2021, 11, 12)
                        ),
                        onDateSelected = { t : LocalDate? -> pickedDate = t}
                    )
                }
            } // end

            Spacer(modifier = Modifier.size(25.dp))

            Text(
                text = "Narednih 5 dana:",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.size(20.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Row(
                    modifier = Modifier
                        .background(color = LightGray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.property_2),
                        contentScale = ContentScale.Inside,
                        contentDescription = "Property 2",
                        modifier = Modifier
                            .size(width = 140.dp, height = 110.dp)
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
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            //horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Icon(
                                imageVector = Icons.Outlined.QueryBuilder,
                                contentDescription = "Clock",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Black
                            )
                            Text(
                                text = "5. oktobar 2021.\n10.30 časova",
                                modifier = Modifier.absolutePadding(5.dp,1.dp,0.dp,0.dp),
                                color = Color.Black,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Left)

                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Icon(
                                imageVector = Icons.Outlined.AddLocation,
                                contentDescription = "Location",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Black
                            )
                            Text(
                                text = "Zmaj Jovina 14,\nKragujevac 34000",
                                modifier = Modifier.absolutePadding(5.dp,1.dp,0.dp,0.dp),
                                color = Color.Black,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Left)

                        }

                    }
                }
            }

            Spacer(modifier = Modifier.size(15.dp))

            // Button
            Row(
                horizontalArrangement = Arrangement.End,

                modifier = Modifier.fillMaxWidth()

            ) {
                Button(
                    onClick = {
                        // akcija
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .clip( RoundedCornerShape(
                            topStartPercent = 5,
                            bottomEndPercent = 5,
                            bottomStartPercent = 5,
                            topEndPercent = 5
                        )),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Cyan,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(
                        start = 5.dp,
                        top = 15.dp,
                        end = 5.dp,
                        bottom = 15.dp
                    )
                )
                {
                    Icon(
                        Icons.Filled.AddLocation,
                        contentDescription = "Mapa",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))

                    Text(text = "Prikaži na mapi", color = Color.White, fontSize = 16.sp)
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun ScheduledToursScreenPreview () {
    SkuciSeTheme {
        ScheduledToursScreen({})
    }
}