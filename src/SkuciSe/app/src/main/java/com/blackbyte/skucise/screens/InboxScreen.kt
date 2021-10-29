package com.blackbyte.skucise.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.InboxMessage
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

@Composable
fun InboxScreen(
    messages: List<InboxMessage> = listOf()
){
    var text by remember { mutableStateOf("") }
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
                            if (it.length <= 100) text = it
                        },
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier
                            .weight(0.8f)
                            .wrapContentHeight()
                            .padding(5.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,
                            cursorColor = Color.Black,
                            disabledLabelColor = Color.LightGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(Modifier.width(15.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(7.dp))
                    ) {
                        IconButton({}, Modifier.padding(4.dp)){
                            Icon(Icons.Filled.Send, "")
                        }
                    }
                    Spacer(Modifier.width(15.dp))
                }
            }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 70.dp)
        ){
            LazyColumn(reverseLayout = true, verticalArrangement = Arrangement.Bottom) {
                items(15) { item ->
                    InboxMessage("asdfasfdasasdfffffffffffffffffffffffffffffffffffffffffff" +
                            "asdfffffffff  $item", "asfdas" ,from=1).DisplayMessage()
                }
                items(15) { item ->
                    InboxMessage("asdfasfdasasdfffffffffffffffffffffffffffffffffffffffffff" +
                            "asdfffffffff  $item", "asfdas" ,from=0).DisplayMessage()
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
fun InboxScreenPreview(){
    SkuciSeTheme {
        InboxScreen()
    }
}