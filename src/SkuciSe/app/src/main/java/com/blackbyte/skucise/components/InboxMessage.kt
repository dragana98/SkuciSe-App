package com.blackbyte.skucise.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

enum class MsgType(val type: Int){
    TEXT(0),
    IMAGE(1)
}

class InboxMessage(
    val text: String = "",
    val time: String = "",
    val type: MsgType = MsgType.TEXT,
    val from: Int = 0
)
{

    @Composable
    fun DisplayMessage(modifier: Modifier = Modifier){
        var expanded by rememberSaveable{ mutableStateOf(false)}
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = if(from==0)Arrangement.End else Arrangement.Start
        ){
            if(from == 0)
                Spacer(modifier = Modifier.width(100.dp))
            Column(
                Modifier
                    .weight(0.8f)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {

                Surface(
                    color = if (from == 0) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(3.dp),
                    modifier = Modifier
                        .align(if (from == 0) Alignment.End else Alignment.Start)
                        .wrapContentSize()
                        .clickable(
                            onClick = {
                                expanded = !expanded
                            }
                        )
                ) {
                    Text(text, modifier = Modifier.padding(5.dp))
                }
                if(expanded) {
                    Text(
                        time,
                        modifier = if (from == 0) Modifier.align(Alignment.End) else Modifier.align(
                            Alignment.Start
                        ),
                        style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Light)
                    )
                }
            }
            if(from == 1)
            Spacer(modifier = Modifier.width(100.dp))
        }
    }
}

var inbx = InboxMessage("The quick brown fox jumps over the lazy dog", "asdfas 18", MsgType.TEXT, 0)

@Preview(
    showBackground = true,
    widthDp = 400,
    heightDp = 200
)
@Composable
fun MessagePreview(){
    inbx.DisplayMessage()
}