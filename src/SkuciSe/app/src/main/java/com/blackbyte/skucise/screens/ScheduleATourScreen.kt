package com.blackbyte.skucise.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.components.DatePickerGrid
import com.blackbyte.skucise.components.NavTopBar
import java.time.LocalDate
import com.blackbyte.skucise.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

@Composable
fun ScheduleATourScreen(
    returnToPreviousScreen: () -> Unit
) {
    var buttonText by rememberSaveable { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    var scrollState = rememberScrollState()
    var nazivClick by rememberSaveable {
        mutableStateOf(false)
    }
    var vlasnistvoClick by rememberSaveable {
        mutableStateOf(false)
    }
    var odgovornoLiceClick by rememberSaveable {
        mutableStateOf(false)
    }
    var kontaktClick by rememberSaveable {
        mutableStateOf(false)
    }
    var name by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            NavTopBar(
                title = "Zakažite obilazak",
                returnToPreviousScreen = returnToPreviousScreen
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp)
                .verticalScroll(scrollState)
        ) {
            Box(Modifier.size(height = 200.dp, width = 400.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.property_2),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxSize(),
                    contentDescription = ""
                )
            }
            Spacer(Modifier.height(15.dp))
            Text(
                "Informacije",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.Start)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        "Naziv objekta",
                        modifier = Modifier.width(120.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(Modifier.width(15.dp))
                    Text("Apartmani Petrović",
                        maxLines = if (nazivClick) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clickable { nazivClick = !nazivClick }
                    )
                }
                Row() {
                    Text(
                        "U vlasništvu",
                        modifier = Modifier.width(120.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(Modifier.width(15.dp))
                    Text("GHP Management d.o.o.",
                        maxLines = if (vlasnistvoClick) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clickable { vlasnistvoClick = !vlasnistvoClick })
                }
                Row() {
                    Text(
                        "Odgovorno lice",
                        modifier = Modifier.width(120.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(Modifier.width(15.dp))
                    Text("Ana Đurđević",
                        maxLines = if (odgovornoLiceClick) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clickable { odgovornoLiceClick = !odgovornoLiceClick })
                }
                Row() {
                    Text(
                        "Kontakt",
                        modifier = Modifier.width(120.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(Modifier.width(15.dp))
                    Text("+381 (0)64 97-49-754",
                        maxLines = if (kontaktClick) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clickable { kontaktClick = !kontaktClick })
                }
            }
            Spacer(Modifier.height(15.dp))
            Text(
                "Izaberite datum i vreme",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.Start)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                DatePickerGrid(onDateSelected = { input ->
                    if (input != null) {
                        buttonText =
                            input.dayOfMonth.toString() + "." + input.monthValue.toString() + "." + input.year.toString()
                    }
                }, date = LocalDate.now())
            }
            Spacer(Modifier.height(15.dp))
            Text(
                "Obilazak je moguće otkazati ili odgoditi najkasnije 1 dan pred zakazani termin. Propušteni obilasci podležu sankcijama.",
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.W500),
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(15.dp))
            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text("Zakazati - $buttonText")
            }
        }

    }

}