package com.blackbyte.skucise.screens


import android.text.style.AlignmentSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
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
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.components.OutlinedInputField
import com.blackbyte.skucise.components.OutlinedPasswordField
import com.blackbyte.skucise.ui.theme.SkuciSeTheme


@Preview
@Composable
fun SavedEntries(returnToPreviousScreen: () -> Unit = {}) {
    SkuciSeTheme {
        Scaffold(
            topBar = { NavTopBar("Sačuvani oglasi", returnToPreviousScreen = returnToPreviousScreen) },
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight() // verticalArrangement = Arrangement.Center
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Row(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Color.LightGray)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.property_2),
                            contentScale = ContentScale.Inside,
                            contentDescription = "Property 2",
                            modifier = Modifier.size(width = 120.dp, height = 90.dp).clip(
                                RoundedCornerShape(topStartPercent = 10,bottomEndPercent = 0,bottomStartPercent = 10,topEndPercent = 0)).border(1.dp,Color.Gray, // RoundedCornerShape(percent = 10)
                                RoundedCornerShape(topStartPercent = 10,bottomEndPercent = 0,bottomStartPercent = 10,topEndPercent = 0))

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
                                text = "200.00 - 600.00e, mesečno",
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
                                modifier = Modifier.fillMaxWidth().absolutePadding(0.dp,3.dp,10.dp,0.dp)
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
                            }//col
                        }//row
                    }//row
                }//col
            }//scaf
        }
    }
