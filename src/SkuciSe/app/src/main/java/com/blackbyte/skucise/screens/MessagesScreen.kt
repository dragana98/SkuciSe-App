package com.blackbyte.skucise.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.dynamicanimation.animation.FlingAnimation
import com.blackbyte.skucise.components.Chat
import com.blackbyte.skucise.components.InboxMessage
import com.blackbyte.skucise.components.MsgType
import com.blackbyte.skucise.R

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun MessagesScreen(){



    var alertState: Boolean by rememberSaveable {mutableStateOf(false)}
    var chats:MutableList<Chat> by rememberSaveable { mutableStateOf(mutableListOf()) }

    chats = mutableListOf(
        Chat("posiljalac", InboxMessage("Poslednja poruka duga duga duga dugacka asdfasdfasfdasdfasfdasfdafdasdfasdfasdfasdfasfdasfasasfasfada", "Vreme je: 12h", MsgType.TEXT, 1), true)
        ,Chat("posiljalac",InboxMessage("Procitana poruka", time = "nzm vreme", MsgType.TEXT, 1), false)
    )
    Scaffold(
        topBar =
        {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                //TopAppBar Content
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                        Modifier.wrapContentWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Text("Poruke", style = MaterialTheme.typography.subtitle1)
                    }
                    Box(
                        Modifier.width(50.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                    }
                }
            }
        }

    ){
        if(alertState){
            AlertDialog(
                onDismissRequest = {
                    alertState = false
                },
                buttons = {
                    Button({}) {Text("obrisi")}
                },
            )
        }
        LazyColumn(){
            items(chats.size){
                 index -> Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .combinedClickable(true, onClick = {},
                            onLongClick = {
                                alertState = true
                            }
                        ),
                    color = if(chats[index].newMsg) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,

                ) {
                    Row(
                        Modifier.padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        Image(
                        painterResource(id = R.drawable.profile_pic_vendor),
                        "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(52.dp)
                        )
                        Column(
                            modifier = Modifier.padding(start = 10.dp, end = 5.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ){
                            Row(
                                modifier = Modifier
                                    .padding(bottom = 3.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    chats[index].sender,
                                    style = MaterialTheme.typography.subtitle1,
                                    overflow = TextOverflow.Ellipsis, maxLines = 1,
                                )
                                if(chats[index].newMsg){
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
                                chats[index].lastText.text,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.padding(top =  3.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
