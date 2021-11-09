package com.blackbyte.skucise.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.data.listOfAmenities
import com.blackbyte.skucise.data.listOfCities
import com.blackbyte.skucise.data.listOfObjects
import com.blackbyte.skucise.data.stars
import com.blackbyte.skucise.ui.theme.Purple500
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material.icons.outlined.BedroomParent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.blackbyte.skucise.ui.theme.Cyan
import com.blackbyte.skucise.ui.theme.TinyGray

@Composable
fun CallAlertDialog() {
    val isDialogOpen = remember { mutableStateOf(false)}

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ShowAlertDialog(isDialogOpen)

        Button(
            onClick = {
                isDialogOpen.value = true
            },
            modifier = Modifier
                .padding(10.dp)
                .height(50.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(TinyGray) // color of button
        ) {
            Text(
                text = "Dodaj sobu +",
                color = Color.Black
            )
        }
    }
}


@Composable
fun ShowAlertDialog(isDialogOpen: MutableState<Boolean>) {
    val buttonOdrasli = remember { mutableStateOf(0) }
    var buttonSobe = remember { mutableStateOf(0) }
    val buttonDeca = remember { mutableStateOf(0) }


    if(isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(340.dp)
                    .height(470.dp)
                    .padding(5.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                color = Color.White,
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Sobe",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )

                        Icon(
                            imageVector = Icons.Outlined.BedroomParent,
                            contentDescription = "Bedroom",
                            modifier = Modifier.size(35.dp),
                            tint = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    /*
                            COLUMNS

                     */

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Sobe: ",
                            textAlign = TextAlign.Right,
                            modifier = Modifier.width(70.dp)
                        )
                        Card(
                            elevation = 2.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(7.dp).width(30.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally)
                            {
                                Text("${buttonSobe.value}", color = Color.Gray)
                            }
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Button(
                            onClick = {
                                      buttonSobe.value++
                            },
                            modifier = Modifier
                                .width(50.dp)
                                .clip( RoundedCornerShape(
                                    topStartPercent = 5,
                                    bottomEndPercent = 5,
                                    bottomStartPercent = 5,
                                    topEndPercent = 5
                                )),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Cyan,
                                contentColor = Color.White
                            )
                        ){
                            Text(text = "+", color = Color.White, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Button(
                            onClick = {

                                      if(buttonSobe.value > 0)
                                          buttonSobe.value--
                            },
                            modifier = Modifier
                                .width(50.dp)
                                .clip( RoundedCornerShape(
                                    topStartPercent = 5,
                                    bottomEndPercent = 5,
                                    bottomStartPercent = 5,
                                    topEndPercent = 5
                                )),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Cyan,
                                contentColor = Color.White
                            )
                        ){
                            Text(text = "-", color = Color.White, fontSize = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Odrasli: ",
                            textAlign = TextAlign.Right,
                            modifier = Modifier.width(70.dp)
                        )
                        Card(
                            elevation = 2.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(7.dp).width(30.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally)
                            {
                                Text("${buttonOdrasli.value}", color = Color.Gray)
                            }
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Button(
                            onClick = {
                                buttonOdrasli.value++
                            },
                            modifier = Modifier
                                .width(50.dp)
                                .clip( RoundedCornerShape(
                                    topStartPercent = 5,
                                    bottomEndPercent = 5,
                                    bottomStartPercent = 5,
                                    topEndPercent = 5
                                )),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Cyan,
                                contentColor = Color.White
                            )
                        ){
                            Text(text = "+", color = Color.White, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Button(
                            onClick = {
                                if(buttonOdrasli.value > 0)
                                    buttonOdrasli.value--
                            },
                            modifier = Modifier
                                .width(50.dp)
                                .clip( RoundedCornerShape(
                                    topStartPercent = 5,
                                    bottomEndPercent = 5,
                                    bottomStartPercent = 5,
                                    topEndPercent = 5
                                )),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Cyan,
                                contentColor = Color.White
                            )
                        ){
                            Text(text = "-", color = Color.White, fontSize = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Deca: ",
                            textAlign = TextAlign.Right,
                            modifier = Modifier.width(70.dp)
                        )
                        Card(
                            elevation = 2.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(7.dp).width(30.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally)
                            {
                                Text("${buttonDeca.value}", color = Color.Gray)
                            }
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Button(
                            onClick = {
                                buttonDeca.value++
                            },
                            modifier = Modifier
                                .width(50.dp)
                                .clip( RoundedCornerShape(
                                    topStartPercent = 5,
                                    bottomEndPercent = 5,
                                    bottomStartPercent = 5,
                                    topEndPercent = 5
                                )),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Cyan,
                                contentColor = Color.White
                            )
                        ){
                            Text(text = "+", color = Color.White, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Button(
                            onClick = {
                                if(buttonDeca.value > 0)
                                    buttonDeca.value--
                            },
                            modifier = Modifier
                                .width(50.dp)
                                .clip( RoundedCornerShape(
                                    topStartPercent = 5,
                                    bottomEndPercent = 5,
                                    bottomStartPercent = 5,
                                    topEndPercent = 5
                                )),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Cyan,
                                contentColor = Color.White
                            )
                        ){
                            Text(text = "-", color = Color.White, fontSize = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.padding(15.dp))

                    Button(
                        onClick = {
                            isDialogOpen.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(Purple500)
                    ) {
                        Text(
                            text = "Sačuvaj",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }

}
