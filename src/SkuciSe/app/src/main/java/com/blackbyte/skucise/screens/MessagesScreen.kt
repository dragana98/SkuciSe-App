package com.blackbyte.skucise.screens

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.compose.rememberImagePainter
import com.blackbyte.skucise.MainActivity
import com.blackbyte.skucise.components.Chat
import com.blackbyte.skucise.components.InboxMessage
import com.blackbyte.skucise.components.MsgType
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.utils.Utils
import org.json.JSONObject
import java.time.LocalDate

private val _cards = MutableLiveData<List<Chat>>()

fun chatPreviewsInvokeInit(t: List<Chat>) {
    _cards.postValue(t)
}

fun prepareMessagePreviews() {
    Utils.getAllConversationPreviews(onFinish = fun(body: String, responseCode: Int) {
        var chats = mutableListOf<Chat>()
        if (responseCode == 200) {
            val jsonObj = JSONObject("{\"data\": $body}")
            val objects = jsonObj.getJSONArray("data")


            for (q in 0 until objects.length()) {
                val obj = objects[q] as JSONObject
                val uid = obj.getInt("id")
                val usnd = obj.getInt("usnd")
                val urcv = obj.getInt("urcv")
                val read = obj.getInt("read")
                val contents = obj.getString("contents")
                val date = LocalDate.parse(
                    obj.getString("date").substring(startIndex = 0, endIndex = 10)
                )

                Handler(Looper.getMainLooper()).post {
                    Log.d("MESSAGES", "$uid $usnd $urcv $contents ")
                }

                Utils.getBasicUserData(
                    user_id = if (MainActivity.prefs?.id == usnd) urcv else usnd,

                    onFinish = fun(body: String, responseCode: Int) {

                        if (responseCode == 200) {
                            val jsonObj = JSONObject(body)
                            val name =
                                "${jsonObj.getString("name")} ${jsonObj.getString("surname")}"
                            var avatarUrl = jsonObj.getString("avatar_url")

                            chats.add(
                                Chat(
                                    senderId = if (MainActivity.prefs?.id == usnd) urcv else usnd,
                                    sender = name,
                                    url = avatarUrl,
                                    newMsg = if ((read == 0) && (urcv == MainActivity.prefs?.id)) true else false,
                                    lastText = InboxMessage(
                                        contents,
                                        "${date.dayOfMonth}.${date.monthValue}.${date.year}",
                                        MsgType.TEXT,
                                        usnd
                                    )
                                )
                            )
                        }
                    })
            }

        } else {
            Log.d("REQUEST ERROR", body)
        }

        Handler(Looper.getMainLooper()).post {
            chatPreviewsInvokeInit(chats)
        }
    })

}

@Composable
fun MessagesScreen(
    cardLive: LiveData<List<Chat>> = _cards,
    returnToPreviousScreen: () -> Unit,
    navigateToInbox: (Int) -> Unit
) {
    val chats: List<Chat>? by cardLive.observeAsState()

    Scaffold(
        topBar = {
            NavTopBar(title = "Poruke", returnToPreviousScreen = returnToPreviousScreen)
        }
    ) {
        chats?.let (){ 
            LazyColumn() {
                items(it.size) { index ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        chats?.let {
                                            Log.d("LOAD INDEX", it[index].senderId.toString())
                                            navigateToInbox(it[index].senderId)
                                            prepareMessagePreviews()
                                        }
                                    }
                                )
                            },
                        color = if (it[index].newMsg) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,

                        ) {
                        Row(
                            Modifier.padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                rememberImagePainter(it[index].url),
                                "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(52.dp)
                            )
                            Column(
                                modifier = Modifier.padding(start = 10.dp, end = 5.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 3.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        it[index].sender,
                                        style = MaterialTheme.typography.subtitle1,
                                        overflow = TextOverflow.Ellipsis, maxLines = 1,
                                    )
                                    if (it[index].newMsg) {
                                        Surface(
                                            elevation = 8.dp,
                                            shape = RoundedCornerShape(5.dp),
                                        ) {
                                            Text(
                                                "NOVA PORUKA",
                                                style = MaterialTheme.typography.subtitle2,
                                                modifier = Modifier.padding(horizontal = 6.dp)
                                            )
                                        }
                                    }
                                }
                                Text(
                                    it[index].lastText.text,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.padding(top = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun MessagesScreenPreview() {
    SkuciSeTheme {
        MessagesScreen({}, {})
    }
}
 */