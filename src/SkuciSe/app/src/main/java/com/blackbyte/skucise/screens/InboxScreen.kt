package com.blackbyte.skucise.screens

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
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.InboxMessage
import com.blackbyte.skucise.components.MsgType
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun InboxScreen(

){
    var text by rememberSaveable { mutableStateOf("") }
    var messages: List<InboxMessage> by rememberSaveable { mutableStateOf(mutableListOf()) }
    messages = mutableListOf(
        InboxMessage("Poštovani,\n" +
                "Vaša poseta je zakazana. Molimo Vas proverite da li je zakazani termin evidentiran u Vašem kalendaru.\n" +
                "\n" +
                "Očekujemo Vas!", "13.5.2021. u 9.42 časova", MsgType.TEXT, 0),
        InboxMessage("Poštovani,\n" +
                "Vaša poseta je zakazana. Molimo Vas proverite da li je zakazani termin evidentiran u Vašem kalendaru.\n" +
                "\n" +
                "Očekujemo Vas!", "13.5.2021. u 9.42 časova", MsgType.TEXT, 0),
        InboxMessage("Poštovani,\n" +
                "Vaša poseta je zakazana. Molimo Vas proverite da li je zakazani termin evidentiran u Vašem kalendaru.\n" +
                "\n" +
                "Očekujemo Vas!", "13.5.2021. u 9.42 časova", MsgType.TEXT, 1)
    );
    fun sendMessage(){
        var message = InboxMessage(text, "nzm vreme", MsgType.TEXT, 0)
        text = ""
        messages += message
    }
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
                ){
                    Box(
                        Modifier.width(50.dp),
                        contentAlignment = Alignment.Center
                    ){
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.ArrowBack, "")
                        }
                    }
                    Box(
                        Modifier.wrapContentSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ){
                            Spacer(Modifier.height(3.dp))
                            Image(
                                painterResource(id = R.drawable.profile_pic_vendor),
                                "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(52.dp)
                            )
                            Spacer(Modifier.height(5.dp))
                            Text("GHP Management d.o.o.", style = MaterialTheme.typography.subtitle1)
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
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 3.dp, top = 3.dp)
                        .wrapContentHeight()
                        .fillMaxWidth()
                ){
                    Spacer(Modifier.width(15.dp))
                    TextField(
                        singleLine = false,
                        value = text,
                        onValueChange = {
                            if (it.length <= 400) text = it
                        },
                        placeholder = { Text("Unesite poruku..." )},
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
                            sendMessage()
                            coroutineScope.launch {
                                listState.animateScrollToItem(index = (messages.size-1))
                            }
                        }, Modifier.padding(4.dp)){
                            Icon(Icons.Filled.Send, "")
                        }
                    }
                    Spacer(Modifier.width(15.dp))
                }
            }
        }
    ) {

        LazyColumn(
            state = listState,
            reverseLayout = false,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 70.dp)
        ) {
            items(messages.size){
                    item -> messages[item].DisplayMessage()
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
fun InboxScreenPreview(){
    SkuciSeTheme {
        InboxScreen()
    }
}
