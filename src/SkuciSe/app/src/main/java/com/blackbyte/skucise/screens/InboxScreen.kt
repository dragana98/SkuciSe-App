package com.blackbyte.skucise.screens

import android.os.Handler
import android.os.Looper
import android.provider.Telephony
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.blackbyte.skucise.MainActivity
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.Chat
import com.blackbyte.skucise.components.InboxMessage
import com.blackbyte.skucise.components.MsgType
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.utils.Utils
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.LocalDate

private val _messages = MutableLiveData<List<InboxMessage>>()
private val _strings = MutableLiveData<List<String>>()

fun messagesInvokeInit(t: List<InboxMessage>, u: List<String>) {
    _messages.postValue(t)
    _strings.postValue(u)
}

var otherId: Int = 0

fun inboxScreenPrepare(id: Int) {
    otherId = id
    Utils.getAllMessages(id = id, onFinish = fun(body: String, responseCode: Int) {
        if (responseCode == 200) {
            var _messages = mutableListOf<InboxMessage>()
            var jsonObj = JSONObject("{\"data\": $body}")
            var allObj = jsonObj.getJSONArray("data")
            for (q in 0 until allObj.length()) {
                var singleMessage = allObj[q] as JSONObject

                val usnd = singleMessage.getInt("usnd")
                val urcv = singleMessage.getInt("urcv")
                val contents = singleMessage.getString("contents")
                val date = LocalDate.parse(
                    singleMessage.getString("date").substring(startIndex = 0, endIndex = 10)
                )
                _messages.add(
                    InboxMessage(
                        contents,
                        "${date.dayOfMonth}.${date.monthValue}.${date.year}",
                        MsgType.TEXT,
                        if (usnd == MainActivity.prefs?.id) 0 else 1
                    )
                )
            }
            Utils.getBasicUserData(
                user_id = id,
                onFinish = fun(body: String, responseCode: Int) {
                    if (responseCode == 200) {
                        val jsonObj = JSONObject(body)
                        val name =
                            "${jsonObj.getString("name")} ${jsonObj.getString("surname")}"
                        var avatarUrl = jsonObj.getString("avatar_url")
                        Handler(Looper.getMainLooper()).post {
                            messagesInvokeInit(_messages, listOf<String>(avatarUrl, name))
                        }
                    }
                })
        }
    })
}

@Composable
fun InboxScreen(
    messagesLive: LiveData<List<InboxMessage>> = _messages,
    stringsLive: LiveData<List<String>> = _strings,
    returnToPreviousScreen: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    val messages: List<InboxMessage>? by messagesLive.observeAsState()
    val strings: List<String>? by stringsLive.observeAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 5.dp,
                modifier = Modifier
                    .height(86.dp)
                    .shadow(
                        shape = RectangleShape,
                        elevation = 10.dp,
                        clip = true
                    )
            ) {
                //TopAppBar Content
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier.width(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                returnToPreviousScreen()
                            }) {
                            Icon(Icons.Filled.ArrowBack, "")
                        }
                    }
                    Box(
                        Modifier.wrapContentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Spacer(Modifier.height(3.dp))
                            strings?.let {
                                GlideImage(
                                    imageModel = it[0],
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(52.dp)
                                )
                            }
                            Spacer(Modifier.height(5.dp))
                            strings?.let {
                                Text(
                                    it[1],
                                    style = MaterialTheme.typography.subtitle1
                                )
                            }
                            Spacer(Modifier.height(3.dp))
                        }
                    }
                    Box(
                        Modifier.width(50.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier.wrapContentHeight(),
                color = Color.White
            ) {
                messages?.let {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 3.dp, top = 3.dp)
                            .wrapContentHeight()
                            .fillMaxWidth()
                    ) {
                        Spacer(Modifier.width(15.dp))
                        TextField(
                            singleLine = false,
                            value = text,
                            onValueChange = {
                                if (it.length <= 400) text = it
                            },
                            placeholder = { Text("Unesite poruku...") },
                            maxLines = 3,
                            shape = RoundedCornerShape(7.dp),
                            modifier = Modifier
//                            .onFocusEvent { focusState -> when{
//                                    focusState.isFocused -> {
//                                        coroutineScope.launch{
//                                            listState.animateScrollToItem(index = (messages.size-1))
//                                        }
//                                    }
//                                }
//                            }
                                .weight(0.8f)
                                .wrapContentHeight()
                                .padding(5.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.LightGray,
                                cursorColor = Color.Black,
                                disabledLabelColor = Color.LightGray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                        )
                        Spacer(Modifier.width(15.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(7.dp))
                        ) {
                            IconButton(onClick = {
                                Utils.sendMessage(
                                    urcv = otherId,
                                    contents = text,
                                    onFinish = fun (body: String, responseCode: Int) {
                                        Handler(Looper.getMainLooper()).post {
                                            Log.d("MESSAGE SEND RETURNED", responseCode.toString())
                                        }
                                            if(responseCode == 200) {
                                          Handler(Looper.getMainLooper()).post {
                                              text = ""
                                              inboxScreenPrepare(otherId)
                                              coroutineScope.launch {
                                                  listState.animateScrollToItem(index = (it.size - 1))
                                              }
                                          }
                                      }
                                    })
                            }, Modifier.padding(4.dp)) {
                                Icon(Icons.Filled.Send, "")
                            }
                        }
                        Spacer(Modifier.width(15.dp))
                    }
                }
            }
        }
    ) {

        messages?.let {
            LazyColumn(
                state = listState,
                reverseLayout = false,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 70.dp)
            ) {
                items(it.size) { item ->
                    it[item].DisplayMessage()
                }
            }
        }
    }
}



@Preview(
    heightDp = 800,
    widthDp = 400,
    showBackground = true
)
@Composable
fun InboxScreenPreview() {
    SkuciSeTheme {
        InboxScreen(returnToPreviousScreen = {})
    }
}
