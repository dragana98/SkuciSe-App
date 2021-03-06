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
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blackbyte.skucise.MainActivity.Companion.prefs
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.data.Ad
import com.blackbyte.skucise.data.listOfAmenities
import com.blackbyte.skucise.data.listOfObjects
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.utils.Config
import com.blackbyte.skucise.utils.Utils
import com.skydoves.landscapist.glide.GlideImage
import org.json.JSONObject
import java.io.ByteArrayOutputStream

private val _entries = MutableLiveData<List<Ad>>()

fun adScreenInvokeInit(t: List<Ad>) {
    _entries.postValue(t)
}


@Composable
fun AdScreen(
    entriesLive: LiveData<List<Ad>> = _entries,
    returnToPreviousScreen: () -> Unit,
    navigateToAdvertise: () -> Unit
) {
    val entries: List<Ad>? by entriesLive.observeAsState()


    var isNewRealtyButtonEnabled by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var corporateAddress by remember { mutableStateOf("") }
    var websiteUrl by remember { mutableStateOf("") }
    var avatarUrl by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val context = LocalContext.current

    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value =
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }

    Scaffold(
        topBar = { NavTopBar("Moji oglasi", returnToPreviousScreen = returnToPreviousScreen) },
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dodaj novi oglas",
                    fontSize = 19.sp
                )
                Spacer(modifier = Modifier.size(size = 20.dp))
                Button(
                    enabled = isNewRealtyButtonEnabled,
                    onClick = {
                        navigateToAdvertise()
                    }, modifier = Modifier
                        .width(70.dp)
                        .height(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    ) {
        if (prefs?.realtorId == 0) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    "Da biste kreirali oglase morate se registrovati kao stanodavac.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(size = 18.dp))
                OutlinedInputField(
                    "Naziv preduze??a",
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(size = 18.dp))
                OutlinedInputField(
                    "Sedi??te",
                    onValueChange = { corporateAddress = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(size = 18.dp))
                OutlinedInputField(
                    "Adresa internet stranice",
                    onValueChange = { websiteUrl = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(size = 18.dp))
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
                            Text(text = "Za??titni znak")
                        }
                        Icon(
                            imageVector = Icons.Default.Photo,
                            contentDescription = "logotype"
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 96.dp)
            ) {
                Button(
                    onClick = {
                        bitmap.value?.let {
                            val baos = ByteArrayOutputStream()
                            it.compress(Bitmap.CompressFormat.JPEG, 60, baos)
                            val imageInByte = baos.toByteArray()

                            Utils.uploadFile(hexdata = imageInByte,
                                onFinish = fun(body: String, responseCode: Int) {
                                    if (responseCode == 200) {
                                        val jsonObj = JSONObject(body)
                                        val rawtext = jsonObj.getString("filename")
                                        avatarUrl =
                                            "http://${Config.FILESERVER_ADDRESS}:${Config.FILEPORT}/$rawtext"
                                        Log.d("REALTOR AVATARURL", avatarUrl)
                                        Utils.addNewRealtor(natural_person = 0,
                                            name = name,
                                            corporate_address = corporateAddress,
                                            website_url = websiteUrl,
                                            avatar_url = avatarUrl,
                                            onFinish = fun(body: String, responseCode: Int) {
                                                if (responseCode == 200) {
                                                    val jsonObj = JSONObject(body)
                                                    val id = jsonObj.getInt("id")
                                                    Handler(Looper.getMainLooper()).post {
                                                        prefs?.realtorId = id
                                                        returnToPreviousScreen()
                                                    }
                                                } else {
                                                }
                                                Handler(Looper.getMainLooper()).post {
                                                    Log.d("GOT ERROR", body)
                                                }
                                            }
                                        )
                                    }
                                })
                        }
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Prijavite nalog")
                }
            }
        } else {
            isNewRealtyButtonEnabled = true
            entries?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(it.size) { index ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                            //, color = background(Color.LightGray)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            )
                            {
                                Row(
                                    modifier = Modifier
                                        //.fillMaxWidth()
                                        //.clip(RoundedCornerShape(10.dp))
                                        .background(Color.LightGray)
                                ) {
                                    GlideImage(
                                        imageModel = it[index].url,
                                        contentScale = ContentScale.Crop,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(width = 120.dp, height = 90.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    topStartPercent = 10,
                                                    bottomEndPercent = 0,
                                                    bottomStartPercent = 10,
                                                    topEndPercent = 0
                                                )
                                            )
                                            .border(
                                                1.dp,
                                                Color.Gray, // RoundedCornerShape(percent = 10)
                                                RoundedCornerShape(
                                                    topStartPercent = 10,
                                                    bottomEndPercent = 0,
                                                    bottomStartPercent = 10,
                                                    topEndPercent = 0
                                                )
                                            )
                                    )

                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        modifier = Modifier.fillMaxWidth()

                                    ) {
                                        Text(
                                            text = it[index].name,
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
                                        Spacer(modifier = Modifier.size(18.dp))
                                        Row(
                                            verticalAlignment = Alignment.Bottom,
                                            horizontalArrangement = Arrangement.End,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .absolutePadding(0.dp, 3.dp, 10.dp, 0.dp)
                                        ) {
                                            Text(
                                                text = "Detalji",
                                                modifier = Modifier.absolutePadding(
                                                    0.dp,
                                                    2.dp,
                                                    0.dp,
                                                    0.dp
                                                ),
                                                textAlign = TextAlign.Left,
                                                color = Color.Blue
                                            )
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = "Details",
                                                modifier = Modifier.size(16.dp),
                                                tint = Color.Blue
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(size = 12.dp))
                    }
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun AdScreenPreview() {
    SkuciSeTheme {
        AdScreen({}, {})
    }
}
 */