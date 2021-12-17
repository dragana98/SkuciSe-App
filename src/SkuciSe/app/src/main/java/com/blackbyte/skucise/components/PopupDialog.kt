package com.blackbyte.skucise.components


import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.data.listOfAmenities
import com.blackbyte.skucise.data.stars
import com.blackbyte.skucise.ui.theme.Purple500
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material.icons.outlined.BedroomParent
import androidx.compose.material.icons.outlined.House
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.blackbyte.skucise.ui.theme.Cyan
import com.blackbyte.skucise.ui.theme.TinyGray
import com.blackbyte.skucise.utils.Config
import com.blackbyte.skucise.utils.Utils
import org.json.JSONObject
import java.io.ByteArrayOutputStream


@Composable
fun CallPopupDialog(
    unified: String,
    leasable: String,
    results: MutableList<List<Pair<String, Any?>>>
) {
    val isUnifiedDialogOpen = remember { mutableStateOf(false) }
    val isNotUnifiedDialogOpen = remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (unified == "Stan" || unified == "Apartman") {
        val buttonToalet = remember { mutableStateOf(0) }
        var buttonSobe = remember { mutableStateOf(0) }
        var povrsina by remember { mutableStateOf("") }
        var cena = remember { mutableStateOf(0F) }
        var depozit = remember { mutableStateOf(0F) }

        var buttonDeca = remember { mutableStateOf(0) } // buton deca
        if (isNotUnifiedDialogOpen.value) {
            Dialog(onDismissRequest = { isNotUnifiedDialogOpen.value = false }) {
                Surface(
                    modifier = Modifier
                        .height(740.dp)
                        .padding(5.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    shape = RoundedCornerShape(5.dp),
                    color = Color.White,
                ) {
                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                        //verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.padding(5.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = unified,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )

                            Icon(
                                imageVector = Icons.Outlined.House,
                                contentDescription = "Bedroom",
                                modifier = Modifier.size(30.dp),
                                tint = Color.Gray
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "NAMENJEN ZA: ",
                                fontSize = 12.sp
                            )
                            Text(
                                text = leasable,
                                fontSize = 13.sp,
                                color = MaterialTheme.colors.primary
                            )
                        }

                        Spacer(modifier = Modifier.padding(15.dp))

                        /*
                                COLUMNS

                         */

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            var monthlyPrice = ""

                            if (leasable == "iznajmljivanje")
                                monthlyPrice = "\nmesečno "

                            Text(
                                text = "Cena  " + monthlyPrice
                            )

                            CustomTextField("0.00  ${Config.CURRENCY}", Modifier.width(110.dp), onValueChange = {cena.value = it.toFloat()})
                        }

                        if (leasable == "iznajmljivanje") {
                            Spacer(modifier = Modifier.size(10.dp))


                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = " Depozit  "
                                )

                                CustomTextField("0.00  ${Config.CURRENCY}", Modifier.width(110.dp), onValueChange = {depozit.value = it.toFloat()})
                            }
                        }

                        Spacer(modifier = Modifier.size(30.dp))




                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Površina  "
                            )

                            OutlinedTextField(
                                value = povrsina,
                                onValueChange = { povrsina = it },
                                modifier = Modifier.width(100.dp),
                                placeholder = { Text("placeholder") }
                            )
                        }
                        Spacer(modifier = Modifier.size(30.dp))




                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Sobe: ",
                                textAlign = TextAlign.Right,
                                modifier = Modifier.width(75.dp)
                            )
                            Card(
                                elevation = 2.dp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(7.dp)
                                        .width(30.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
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
                                    .clip(
                                        RoundedCornerShape(
                                            topStartPercent = 5,
                                            bottomEndPercent = 5,
                                            bottomStartPercent = 5,
                                            topEndPercent = 5
                                        )
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Cyan,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "+", color = Color.White, fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Button(
                                onClick = {

                                    if (buttonSobe.value > 0)
                                        buttonSobe.value--
                                },
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStartPercent = 5,
                                            bottomEndPercent = 5,
                                            bottomStartPercent = 5,
                                            topEndPercent = 5
                                        )
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Cyan,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "-", color = Color.White, fontSize = 16.sp)
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Kupatila: ",
                                textAlign = TextAlign.Right,
                                modifier = Modifier.width(75.dp)
                            )
                            Card(
                                elevation = 2.dp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(7.dp)
                                        .width(30.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
                                {
                                    Text("${buttonToalet.value}", color = Color.Gray)
                                }
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Button(
                                onClick = {
                                    buttonToalet.value++
                                },
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStartPercent = 5,
                                            bottomEndPercent = 5,
                                            bottomStartPercent = 5,
                                            topEndPercent = 5
                                        )
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Cyan,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "+", color = Color.White, fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Button(
                                onClick = {
                                    if (buttonToalet.value > 0)
                                        buttonToalet.value--
                                },
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStartPercent = 5,
                                            bottomEndPercent = 5,
                                            bottomStartPercent = 5,
                                            topEndPercent = 5
                                        )
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Cyan,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "-", color = Color.White, fontSize = 16.sp)
                            }
                        }

                        Spacer(modifier = Modifier.size(35.dp))



                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Raspored prostorija",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            Text(
                                text = "Dodati fotografiju koja predstavlja arhitektonsko idejno rešenje rasporeda prostorija u objektu.",
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.size(1.dp))
                        }

                        var imageUrl by remember { mutableStateOf<Uri?>(null) }
                        val context = LocalContext.current
                        val bitmap = remember { mutableStateOf<Bitmap?>(null) }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetContent()
                        ) { uri: Uri? ->
                            imageUrl = uri
                        }

                        Column {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .padding(12.dp)
                                    .height(50.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                imageUrl?.let {
                                    if (Build.VERSION.SDK_INT < 28) {
                                        bitmap.value = MediaStore.Images.Media.getBitmap(
                                            context.contentResolver,
                                            it
                                        )
                                    } else {
                                        val source =
                                            ImageDecoder.createSource(context.contentResolver, it)
                                        bitmap.value = ImageDecoder.decodeBitmap(source)
                                    }

                                    bitmap.value?.let { bitmap ->
                                        Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = "Gallery Image",
                                            modifier = Modifier.size(400.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.padding(5.dp))

                                Button(
                                    onClick = {
                                        launcher.launch("image/*")
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Cyan
                                    )
                                ) {
                                    Text(
                                        text = "Dodaj sliku",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.size(25.dp))

                        Button(
                            onClick = {
                                bitmap.value?.let {
                                    val baos = ByteArrayOutputStream()
                                    it.compress(Bitmap.CompressFormat.JPEG, 60, baos)
                                    val imageInByte = baos.toByteArray()

                                    Utils.uploadFile(hexdata = imageInByte,
                                        onFinish = fun(body: String, responseCode: Int) {
                                            Handler(Looper.getMainLooper()).post(Runnable {
                                                if (responseCode == 200) {
                                                    val jsonObj = JSONObject(body)
                                                    val rawtext = jsonObj.getString("filename")
                                                    val archURL =
                                                        "http://${Config.FILESERVER_ADDRESS}:${Config.FILEPORT}/$rawtext"
                                                    Log.d("archURL", archURL)
                                                    results.add(
                                                        listOf(
                                                            Pair<String, Int>(
                                                                "number_of_rooms",
                                                                buttonSobe.value
                                                            ),
                                                            Pair<String, Int>(
                                                                "number_of_bathrooms",
                                                                buttonToalet.value
                                                            ),
                                                            Pair<String, Int>(
                                                                "deposit",
                                                                depozit.value.toInt()
                                                            ),
                                                            Pair<String, Int>(
                                                                "price",
                                                                cena.value.toInt()
                                                            ),
                                                            Pair<String, Int>(
                                                                "surface",
                                                                povrsina.toInt()
                                                            ),
                                                            Pair<String, String>(
                                                                "floor_plan_url",
                                                                archURL
                                                            )
                                                        )
                                                    )



                                                    isNotUnifiedDialogOpen.value = false
                                                }
                                            })
                                        })
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
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

    } else {
        val buttonToalet = remember { mutableStateOf(0) }
        var buttonSobe = remember { mutableStateOf(0) }
        var povrsina by remember { mutableStateOf("") }
        var cena = remember { mutableStateOf(0F) }
        var depozit = remember { mutableStateOf(0F) }

        remember { mutableStateOf(0) } // buton deca


        if (isUnifiedDialogOpen.value) {
            Dialog(onDismissRequest = { isUnifiedDialogOpen.value = false }) {
                Surface(
                    modifier = Modifier
                        .height(740.dp)
                        .padding(5.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    shape = RoundedCornerShape(5.dp),
                    color = Color.White,
                ) {
                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                        //verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.padding(5.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = unified,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )

                            Icon(
                                imageVector = Icons.Outlined.House,
                                contentDescription = "Bedroom",
                                modifier = Modifier.size(30.dp),
                                tint = Color.Gray
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "NAMENJEN ZA: ",
                                fontSize = 12.sp
                            )
                            Text(
                                text = leasable,
                                fontSize = 13.sp,
                                color = MaterialTheme.colors.primary
                            )
                        }

                        Spacer(modifier = Modifier.padding(15.dp))

                        /*
                                COLUMNS

                         */

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            var monthlyPrice = ""

                            if (leasable == "iznajmljivanje")
                                monthlyPrice = "\nmesečno "

                            Text(
                                text = "Cena  " + monthlyPrice
                            )

                            CustomTextField("0.00  ${Config.CURRENCY}", Modifier.width(110.dp), onValueChange = {cena.value = it.toFloat()})
                        }

                        if (leasable == "iznajmljivanje") {
                            Spacer(modifier = Modifier.size(10.dp))


                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = " Depozit  "
                                )

                                CustomTextField("0.00  ${Config.CURRENCY}", Modifier.width(110.dp), onValueChange = {depozit.value = it.toFloat()})
                            }
                        }

                        Spacer(modifier = Modifier.size(30.dp))




                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Površina  "
                            )


                            OutlinedTextField(
                                value = povrsina,
                                onValueChange = { povrsina = it },
                                modifier = Modifier.width(100.dp),
                                placeholder = { Text("placeholder") }
                            )
                        }
                        Spacer(modifier = Modifier.size(30.dp))




                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Sobe: ",
                                textAlign = TextAlign.Right,
                                modifier = Modifier.width(75.dp)
                            )
                            Card(
                                elevation = 2.dp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(7.dp)
                                        .width(30.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
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
                                    .clip(
                                        RoundedCornerShape(
                                            topStartPercent = 5,
                                            bottomEndPercent = 5,
                                            bottomStartPercent = 5,
                                            topEndPercent = 5
                                        )
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Cyan,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "+", color = Color.White, fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Button(
                                onClick = {

                                    if (buttonSobe.value > 0)
                                        buttonSobe.value--
                                },
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStartPercent = 5,
                                            bottomEndPercent = 5,
                                            bottomStartPercent = 5,
                                            topEndPercent = 5
                                        )
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Cyan,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "-", color = Color.White, fontSize = 16.sp)
                            }
                        }

                        Spacer(modifier = Modifier.size(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Kupatila: ",
                                textAlign = TextAlign.Right,
                                modifier = Modifier.width(75.dp)
                            )
                            Card(
                                elevation = 2.dp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(7.dp)
                                        .width(30.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
                                {
                                    Text("${buttonToalet.value}", color = Color.Gray)
                                }
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Button(
                                onClick = {
                                    buttonToalet.value++
                                },
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStartPercent = 5,
                                            bottomEndPercent = 5,
                                            bottomStartPercent = 5,
                                            topEndPercent = 5
                                        )
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Cyan,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "+", color = Color.White, fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Button(
                                onClick = {
                                    if (buttonToalet.value > 0)
                                        buttonToalet.value--
                                },
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStartPercent = 5,
                                            bottomEndPercent = 5,
                                            bottomStartPercent = 5,
                                            topEndPercent = 5
                                        )
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Cyan,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "-", color = Color.White, fontSize = 16.sp)
                            }
                        }

                        Spacer(modifier = Modifier.size(35.dp))



                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Raspored prostorija",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            Text(
                                text = "Dodati fotografiju koja predstavlja arhitektonsko idejno rešenje rasporeda prostorija u objektu.",
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.size(1.dp))
                        }

                        var imageUrl by remember { mutableStateOf<Uri?>(null) }
                        val context = LocalContext.current
                        val bitmap = remember { mutableStateOf<Bitmap?>(null) }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetContent()
                        ) { uri: Uri? ->
                            imageUrl = uri
                        }

                        Column {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .padding(12.dp)
                                    .height(50.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                imageUrl?.let {
                                    if (Build.VERSION.SDK_INT < 28) {
                                        bitmap.value = MediaStore.Images.Media.getBitmap(
                                            context.contentResolver,
                                            it
                                        )
                                    } else {
                                        val source =
                                            ImageDecoder.createSource(context.contentResolver, it)
                                        bitmap.value = ImageDecoder.decodeBitmap(source)
                                    }

                                    bitmap.value?.let { bitmap ->
                                        Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = "Gallery Image",
                                            modifier = Modifier.size(400.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.padding(5.dp))

                                Button(
                                    onClick = {
                                        launcher.launch("image/*")
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Cyan
                                    )
                                ) {
                                    Text(
                                        text = "Dodaj sliku",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.size(25.dp))

                        Button(
                            onClick = {
                                bitmap.value?.let {
                                    val baos = ByteArrayOutputStream()
                                    it.compress(Bitmap.CompressFormat.JPEG, 60, baos)
                                    val imageInByte = baos.toByteArray()

                                    Utils.uploadFile(hexdata = imageInByte,
                                        onFinish = fun(body: String, responseCode: Int) {
                                            Handler(Looper.getMainLooper()).post(Runnable {
                                                if (responseCode == 200) {
                                                    val jsonObj = JSONObject(body)
                                                    val rawtext = jsonObj.getString("filename")
                                                    val archURL =
                                                        "http://${Config.FILESERVER_ADDRESS}:${Config.FILEPORT}/$rawtext"
                                                    Log.d("archURL", archURL)

                                                    results.add(
                                                        listOf(
                                                            Pair<String, Int>(
                                                                "number_of_rooms",
                                                                buttonSobe.value
                                                            ),
                                                            Pair<String, Int>(
                                                                "number_of_bathrooms",
                                                                buttonToalet.value
                                                            ),
                                                            Pair<String, Int>(
                                                                "deposit",
                                                                depozit.value.toInt()
                                                            ),
                                                            Pair<String, Int>(
                                                                "price",
                                                                cena.value.toInt()
                                                            ),
                                                            Pair<String, Int>(
                                                                "surface",
                                                                povrsina.toInt()
                                                            ),
                                                            Pair<String, String>(
                                                                "floor_plan_url",
                                                                archURL
                                                            )
                                                        )
                                                    )


                                                    isUnifiedDialogOpen.value = false
                                                }
                                            })
                                        })
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
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

    Button(
        onClick = {
            if ((unified == "Stan" || unified == "Apartman") && leasable != "") {
                isNotUnifiedDialogOpen.value = true
                isUnifiedDialogOpen.value = false
            } else if ((unified == "Kuća" || unified == "Vikendica") && leasable != "") {
                isUnifiedDialogOpen.value = true
                isNotUnifiedDialogOpen.value = false
            } else {
                Toast.makeText(context, "Niste izabrali obavezna polja!", Toast.LENGTH_SHORT).show()
            }

        }, modifier = Modifier.fillMaxWidth()
    ) {
        if (unified == "Kuća" && leasable == "kupovinu")
            Text(text = "Dodaj kuću", color = Color.White, fontSize = 16.sp)
        else if ((unified == "Kuća" || unified == "Vikendica") && leasable == "iznajmljivanje")
            Text(text = "Dodaj novi sprat", color = Color.White, fontSize = 16.sp)
        else if (unified == "Vikendica" && leasable == "kupovinu")
            Text(text = "Dodaj vikendicu", color = Color.White, fontSize = 16.sp)
        else if (unified == "Stan" && (leasable == "kupovinu" || leasable == "iznajmljivanje"))
            Text(text = "Dodaj novi stan", color = Color.White, fontSize = 16.sp)
        else if (unified == "Apartman" && (leasable == "kupovinu" || leasable == "iznajmljivanje"))
            Text(text = "Dodaj novi apartman", color = Color.White, fontSize = 16.sp)
        else
            Text(text = "Dodaj novi objekat", color = Color.White, fontSize = 16.sp)
    }


}