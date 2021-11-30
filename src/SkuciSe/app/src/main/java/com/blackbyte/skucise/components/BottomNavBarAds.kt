package com.blackbyte.skucise.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavBarAds(
    navigateToAdvertise: () -> Unit
){
    BottomNavigation() {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Dodaj novi oglas  ",
                fontSize = 19.sp,
                color = Color.White, // color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.size(size = 20.dp))

            Button(
                onClick = {
                    navigateToAdvertise()
                }, modifier = Modifier
                    .width(70.dp)
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(7.dp))
                    .border(
                        1.dp, Color.White
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White // Gray
                )
            ){
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(50.dp)
                    //tint = Color.DarkGray

                )
            }
        }
    }
}