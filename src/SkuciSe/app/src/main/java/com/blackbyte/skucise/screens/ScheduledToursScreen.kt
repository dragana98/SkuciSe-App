package com.blackbyte.skucise.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
                    if(pickedDate != null) {
                        Text("Izabrali ste datum: ${pickedDate.dayOfMonth}.${pickedDate.monthValue}.${pickedDate.year}.")
                    } else {
                        Text("Izaberite datum.")
                    }

                }
            } // end

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Narednih 5 dana:",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primary
            )
            /*

            Dodati oglas

             */

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