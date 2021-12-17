package com.blackbyte.skucise.screens

import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import com.skydoves.landscapist.glide.GlideImage
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
import androidx.compose.runtime.*
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.blackbyte.skucise.components.DatePickerMode
import com.blackbyte.skucise.data.Ad
import com.blackbyte.skucise.utils.Utils


private val _entries = MutableLiveData<List<Any>>()

fun scheduleInvokeInit(t: List<Any>) {
    _entries.postValue(t)
}

@Composable
fun ScheduleATourScreen(
    entriesLive: LiveData<List<Any>> = _entries,
    returnToPreviousScreen: () -> Unit
) {

    val entries: List<Any>? by entriesLive.observeAsState()

    var buttonText by rememberSaveable { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    var scrollState = rememberScrollState()
    var nazivClick by rememberSaveable {
        mutableStateOf(false)
    }
    var kontaktClick by rememberSaveable {
        mutableStateOf(false)
    }

    var date: LocalDate? by remember { mutableStateOf(null) }

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
                entries?.let {
                    GlideImage(
                        imageModel = (it[0]),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .fillMaxSize(),
                        contentDescription = ""
                    )
                }
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
                    entries?.let {
                        Text(it[1] as String,
                            maxLines = if (nazivClick) Int.MAX_VALUE else 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .clickable { nazivClick = !nazivClick }
                        )
                    }
                }
                Row() {
                    Text(
                        "Kontakt",
                        modifier = Modifier.width(120.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(Modifier.width(15.dp))
                    entries?.let {
                        Text(it[2] as String,
                            maxLines = if (kontaktClick) Int.MAX_VALUE else 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .clickable { kontaktClick = !kontaktClick })
                    }
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
                DatePickerGrid(
                    cutoffMode = DatePickerMode.INACTIVATE_PAST_DAYS,
                    onDateSelected = { input ->
                        if (input != null) {
                            date = input
                            buttonText =
                                input.dayOfMonth.toString() + "." + input.monthValue.toString() + "." + input.year.toString()
                        }
                    }, date = LocalDate.now().plusDays(3)
                )
            }
            Spacer(Modifier.height(15.dp))
            Text(
                "Obilazak je moguće otkazati ili odgoditi najkasnije 1 dan pred zakazani termin. Propušteni obilasci podležu sankcijama.",
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.W500),
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(15.dp))
            Button(onClick = {
                entries?.let {
                    Utils.addAvailableTerm(
                        property_ad_id = it[3] as Int,
                        date = date.toString(),
                        onFinish = fun(body: String, responseCode: Int) {
                            if (responseCode == 200) {
                                Handler(Looper.getMainLooper()).post {
                                    returnToPreviousScreen()
                                    returnToPreviousScreen()
                                }
                            }
                        })
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Zakazati - $buttonText")
            }
        }

    }

}