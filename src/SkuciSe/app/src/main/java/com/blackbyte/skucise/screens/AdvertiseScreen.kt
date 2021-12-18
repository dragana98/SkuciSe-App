package com.blackbyte.skucise.screens


import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blackbyte.skucise.MainActivity.Companion.prefs
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.data.FilterAmenities
import com.blackbyte.skucise.data.listOfAmenities
import com.blackbyte.skucise.data.listOfObjects
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.utils.Config
import com.blackbyte.skucise.utils.Utils
import org.json.JSONObject
import java.io.ByteArrayOutputStream

private val _strings = MutableLiveData<List<String>>()

fun stringsInit(u: List<String>) {
    _strings.postValue(u)
}

fun uploadAllImgs(
    currentIndex: Int,
    images: MutableList<String>,
    bitmaps: List<Bitmap>,
    next: () -> Unit
) {
    val baos = ByteArrayOutputStream()
    bitmaps[currentIndex].compress(Bitmap.CompressFormat.JPEG, 60, baos)
    val imageInByte = baos.toByteArray()
    Utils.uploadFile(hexdata = imageInByte,
        onFinish = fun(body: String, responseCode: Int) {
            if (responseCode == 200) {
                val jsonObj = JSONObject(body)
                val rawtext = jsonObj.getString("filename")
                Handler(Looper.getMainLooper()).post(Runnable {
                    images.add("http://${Config.FILESERVER_ADDRESS}:${Config.FILEPORT}/$rawtext")
                    Log.d(
                        "PROPERTY IMG",
                        "http://${Config.FILESERVER_ADDRESS}:${Config.FILEPORT}/$rawtext"
                    )
                })
                if (currentIndex + 1 == bitmaps.size) {
                    Handler(Looper.getMainLooper()).post(Runnable {
                        next()
                    })
                } else {
                    uploadAllImgs(currentIndex + 1, images, bitmaps, next)
                }
            }
        })
}

@Composable
fun AdvertiseScreen(
    stringsLive: LiveData<List<String>> = _strings,
    returnToPreviousScreen: () -> Unit
) {
    val strings: List<String>? by stringsLive.observeAsState()

    Scaffold(
        topBar = { NavTopBar("Oglasi", returnToPreviousScreen = returnToPreviousScreen) },
        backgroundColor = MaterialTheme.colors.background
    ) {
        var shouldAddNewRealty by remember { mutableStateOf(false) }
        val realtyTypes = listOf("Stan", "Vikendica", "Apartman", "Kuća")
        var selectedRealtyTypeIndex by remember { mutableStateOf(0) }
        var name by remember { mutableStateOf("") }
        var city by remember { mutableStateOf("") }
        var postalCode by remember { mutableStateOf("") }
        var streetAddress by remember { mutableStateOf("") }
        var textDescription by remember { mutableStateOf("") }

        var unified by remember { mutableStateOf(true) }
        var leasable by remember { mutableStateOf(true) }

        val context = LocalContext.current

        unified =
            !(realtyTypes[selectedRealtyTypeIndex] == "Stan" || realtyTypes[selectedRealtyTypeIndex] == "Apartman")

        var results = remember { mutableListOf<List<Pair<String, Any?>>>() }
        var imageUris = remember { mutableStateOf(mutableListOf<Uri>()) }
        var bitmaps = remember { mutableListOf<Bitmap>() }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { it1 -> imageUris.value.add(it1) }
        }

        var amenitiesIndex = remember { mutableListOf<Int>(0, 0, 0, 0, 0, 0, 0, 0) }

        val amenitiesEncoded = listOf(
            "wifi",
            "petfriendly",
            "terrace",
            "parking",
            "heating",
            "tv",
            "pool",
            "furnished"
        )

        val triggerAmenity = fun(index: Int) {
            if (amenitiesIndex[index] == 0) {
                amenitiesIndex[index] = 1
            } else {
                amenitiesIndex[index] = 0
            }
        }

        for (imageUri in imageUris.value) {
            if (Build.VERSION.SDK_INT < 28) {
                bitmaps.add(
                    MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                )
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                bitmaps.add(ImageDecoder.decodeBitmap(source))
            }
        }

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight() // verticalArrangement = Arrangement.Center
                .verticalScroll(rememberScrollState())
        ) {

            // Naslov oglasa
            Text(
                text = "Naslov oglasa ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(size = 10.dp))

            CustomTextField(
                "Primer: Apartmani Petrović, Vila Azure...",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { name = it })

            Spacer(modifier = Modifier.size(size = 29.dp))

            // Ponuda objekta
            Text(
                text = "Ovim oglasom se nudi ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(size = 5.dp))

            DropdownButton(
                hintText = realtyTypes[selectedRealtyTypeIndex],
                items = realtyTypes,
                disabled = listOf(),
                onSelectedIndex = { selectedRealtyTypeIndex = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(size = 15.dp))

            Text(
                text = "Fotografije objekta ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )

            Row {
                for (bitmap in bitmaps) {
                    Surface(shape = RoundedCornerShape(percent = 10)) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Gallery Image",
                            modifier = Modifier.size(82.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(14.dp))
                }
            }

            Spacer(modifier = Modifier.size(size = 5.dp))

            OutlinedButton(
                onClick = {
                    launcher.launch("image/*")
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(text = "Izaberite fotografiju")
                    }
                    Icon(
                        imageVector = Icons.Default.Photo,
                        contentDescription = "logotype"
                    )
                }
            }
            Spacer(modifier = Modifier.size(size = 15.dp))

            Text(
                text = "Objekat je namenjen za ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(size = 15.dp))

            var selectedCategory = remember { mutableStateOf("") }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                RadioButton(
                    selected = selectedCategory.value == "iznajmljivanje",
                    onClick = {
                        leasable = true
                        selectedCategory.value = "iznajmljivanje"
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.onBackground
                    )
                )
                Text("iznajmljivanje")

                Spacer(modifier = Modifier.size(size = 40.dp))

                RadioButton(
                    selected = selectedCategory.value == "kupovinu",
                    onClick = {
                        leasable = false
                        selectedCategory.value = "kupovinu"
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.onBackground
                    )
                )
                Text("kupovinu")

            }

            Spacer(modifier = Modifier.size(size = 30.dp))


            Text(
                text = "Lokacija objekta ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(size = 10.dp))

            Column(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .border(1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))

            ) {
                Column(
                    modifier = Modifier
                        .padding(13.dp)
                ) {
                    Text("Grad")

                    CustomTextField(
                        "Beograd",
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { city = it })

                    Spacer(modifier = Modifier.size(size = 20.dp))

                    Text("Poštanski broj")

                    CustomTextField(
                        "11000",
                        modifier = Modifier.width(117.dp),
                        onValueChange = { postalCode = it })

                    Spacer(modifier = Modifier.size(size = 20.dp))

                    Text("Ulica")

                    CustomTextField(
                        "Kralja Milana 10",
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { streetAddress = it })

                    Spacer(modifier = Modifier.size(size = 10.dp))
                }
            }


            // Detalji o objektu - Section

            Spacer(modifier = Modifier.size(size = 25.dp))

            Text(
                text = "Prostorni plan objekta",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(size = 20.dp))

            Text(
                "Ovde možete uneti sve podatke za stanove/apartmane u ponudi " +
                        "(kvadratura, raspored soba, i slično).", fontSize = 14.sp
            )

            Spacer(modifier = Modifier.size(size = 20.dp))

            Text(
                "Ovde možete uneti sve podatke za svaki sprat Vašeg objekta.",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.size(size = 25.dp))

            CallPopupDialog(
                unified = realtyTypes[selectedRealtyTypeIndex],
                leasable = selectedCategory.value,
                results = results
            )

            Spacer(modifier = Modifier.size(size = 15.dp))
            // Amenities

            Column(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
            )
            {
                Spacer(modifier = Modifier.size(size = 45.dp))

                Text(
                    text = "Pogodnosti",
                    fontSize = 19.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(size = 10.dp))

                Text(
                    text = "Izaberite sve pogodnosti koje su uključene u ponudu.",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(size = 10.dp))

                FilterChipAmenities(filters = amenityList, triggerIndex = triggerAmenity)
            }

            // Opis oglasa

            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.size(size = 15.dp))

                Text(
                    text = "Opis oglasa",
                    fontSize = 19.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(size = 10.dp))

                Text(
                    text = "    Stavite naglasak na prednosti objekta - " +
                            "da li su prodavnice, škole i autobuske stanice u blizini?",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(size = 20.dp))

                Text(
                    text = "  Možda je Vaš objekat udaljen od saobraćajnica i bez bučne atmosfere?" +
                            " Ovo je prilika da kupca ubedite da izabere Vašu ponudu!",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(size = 15.dp))


                TextField(
                    value = textDescription,
                    onValueChange = { textDescription = it },
                    placeholder = { Text("Dodajte opis...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

            }
            Spacer(modifier = Modifier.size(size = 20.dp))

            Button(
                onClick = {
                    val _images = mutableListOf<String>()
                    val _amenities = mutableListOf<String>()

                    uploadAllImgs(0,
                        images = _images,
                        bitmaps = bitmaps as List<Bitmap>,
                        next = {
                            for (index in amenitiesIndex) {
                                _amenities.add(amenitiesEncoded[index])
                            }

                            prefs?.realtorId?.let { it1 ->
                                Utils.addNewPropertyAd(
                                    name = name,
                                    realtor_id = it1,
                                    description = textDescription,
                                    city = city,
                                    postal_code = postalCode,
                                    street_address = streetAddress,
                                    leasable = if (leasable) 1 else 0,
                                    unified = if (unified) 1 else 0,
                                    images = _images,
                                    amenities = _amenities,
                                    property_ad_realties = results as List<List<Pair<String, Any?>>>,
                                    price = null,
                                    deposit = null,
                                    onFinish = fun(body: String, responseCode: Int) {
                                        Handler(Looper.getMainLooper()).post(Runnable {
                                            if (responseCode == 200) {
                                                Log.d("SUCCESS", "OK")
                                            } else {
                                                Log.d("ERROR", body)
                                            }
                                        })
                                    }
                                )
                            }

                        })
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Dodaj oglas", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun AdvertiseScreenPreview() {
    SkuciSeTheme {
        AdvertiseScreen({})
    }
}
 */