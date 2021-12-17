package com.blackbyte.skucise.screens

import android.os.Handler
import android.os.Looper
import android.util.Log
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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.DatePickerGrid
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.data.Ad
import com.blackbyte.skucise.ui.theme.Cyan
import com.blackbyte.skucise.ui.theme.LightGray
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.utils.Utils
import com.skydoves.landscapist.glide.GlideImage
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime

private val _dates = MutableLiveData<List<List<Any>>>()
private val _onWait = MutableLiveData<List<List<Any>>>()


fun addScheduledEntries(
    t: List<List<Any>>,
    u: List<List<Any>>
) {
    _dates.postValue(t)
    _onWait.postValue(u)
}

@Composable
fun ScheduledToursScreen(
    datesLive: LiveData<List<List<Any>>> = _dates,
    onWaitLive: LiveData<List<List<Any>>> = _onWait,
    returnToPreviousScreen: () -> Unit
) {

    val dates: List<List<Any>>? by datesLive.observeAsState()
    val onWait: List<List<Any>>? by onWaitLive.observeAsState()


    Scaffold(
        topBar = {
            NavTopBar(
                "Zakazani obilasci",
                returnToPreviousScreen = returnToPreviousScreen
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Text(
                text = "Na čekanju",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primary
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            )
            {
                onWait?.let {
                    it.forEach() {
                        Column {
                            Surface(
                                shape = RoundedCornerShape(
                                    topStartPercent = 10,
                                    bottomEndPercent = 10,
                                    bottomStartPercent = 10,
                                    topEndPercent = 10
                                )
                            ) {
                                Row {
                                    GlideImage(
                                        imageModel = (it[2] as String),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(width = 140.dp, height = 110.dp)
                                    )
                                    Spacer(modifier = Modifier.size(12.dp))
                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        modifier = Modifier.fillMaxWidth()

                                    ) {
                                        Text(
                                            text = it[1] as String,
                                            modifier = Modifier.absolutePadding(
                                                5.dp,
                                                5.dp,
                                                0.dp,
                                                0.dp
                                            ),
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
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.QueryBuilder,
                                                contentDescription = "Clock",
                                                modifier = Modifier.size(16.dp)
                                            )
                                            val tmpdate = LocalDate.parse(
                                                (it[0] as String).substring(
                                                    startIndex = 0,
                                                    endIndex = 10
                                                )
                                            )
                                            val tmptime = (it[0] as String).substring(
                                                startIndex = 11,
                                                endIndex = 16
                                            )
                                            Text(
                                                text = "${tmpdate.dayOfMonth}.${tmpdate.monthValue}.${tmpdate.year}. u $tmptime",
                                                modifier = Modifier.absolutePadding(
                                                    5.dp,
                                                    1.dp,
                                                    0.dp,
                                                    0.dp
                                                ),
                                                textAlign = TextAlign.Left
                                            )

                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.AddLocation,
                                                contentDescription = "Location",
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = it[3] as String,
                                                modifier = Modifier.absolutePadding(
                                                    5.dp,
                                                    1.dp,
                                                    0.dp,
                                                    0.dp
                                                ),
                                                textAlign = TextAlign.Left
                                            )

                                        }

                                    }
                                }
                                Spacer(modifier = Modifier.size(14.dp))
                            }
                            Spacer(modifier = Modifier.size(14.dp))
                            Row {
                                OutlinedButton(
                                    onClick = {
                                        Utils.setScheduleStatus(tour_id = it[4] as Int,
                                            value = LocalDateTime.now().toString(),
                                            onFinish = fun(body: String, responseCode: Int) {
                                                if (responseCode == 200) {
                                                    Handler(Looper.getMainLooper()).post {
                                                        returnToPreviousScreen()
                                                    }
                                                }
                                            })
                                    },
                                    content = @Composable { Text("Potvrdi") })
                                Spacer(modifier = Modifier.size(14.dp))
                                OutlinedButton(
                                    onClick = {
                                        val thisid = it[4] as Int
                                        Utils.setScheduleStatus(tour_id = thisid,
                                            value = "DECLINE",
                                            onFinish = fun(body: String, responseCode: Int) {
                                                if (responseCode == 200) {
                                                    Handler(Looper.getMainLooper()).post {
                                                        returnToPreviousScreen()
                                                    }
                                                }
                                            })
                                    },
                                    content = @Composable { Text("Otkaži") })

                            }

                            Spacer(modifier = Modifier.size(48.dp))
                        }
                    }
                }
            }
            Text(
                text = "Vaši obilasci",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primary
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            )
            {
                dates?.let {
                    it.forEach() {
                        Surface(
                            shape = RoundedCornerShape(
                                topStartPercent = 10,
                                bottomEndPercent = 10,
                                bottomStartPercent = 10,
                                topEndPercent = 10
                            )
                        ) {
                            Row {
                                GlideImage(
                                    imageModel = (it[2] as String),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(width = 140.dp, height = 110.dp)
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier.fillMaxWidth()

                                ) {
                                    Text(
                                        text = it[1] as String,
                                        modifier = Modifier.absolutePadding(5.dp, 5.dp, 0.dp, 0.dp),
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
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.QueryBuilder,
                                            contentDescription = "Clock",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "${(it[0] as LocalDate).dayOfMonth}.${(it[0] as LocalDate).monthValue}.${(it[0] as LocalDate).year}.",
                                            modifier = Modifier.absolutePadding(
                                                5.dp,
                                                1.dp,
                                                0.dp,
                                                0.dp
                                            ),
                                            textAlign = TextAlign.Left
                                        )

                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.AddLocation,
                                            contentDescription = "Location",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = it[3] as String,
                                            modifier = Modifier.absolutePadding(
                                                5.dp,
                                                1.dp,
                                                0.dp,
                                                0.dp
                                            ),
                                            textAlign = TextAlign.Left
                                        )

                                    }

                                }
                            }
                            Spacer(modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }


            // Button
            /*

            Spacer(modifier = Modifier.size(15.dp))
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
             */

        }

    }
}

/*
@Preview(showBackground = true)
@Composable
fun ScheduledToursScreenPreview () {
    SkuciSeTheme {
        ScheduledToursScreen({})
    }
}
 */