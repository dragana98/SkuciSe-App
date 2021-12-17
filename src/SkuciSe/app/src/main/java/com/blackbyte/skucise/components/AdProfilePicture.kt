package com.blackbyte.skucise.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.ui.theme.Cyan


@Composable
fun AdProfilePicture() {

    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUrl = uri
    }
    var imageVectorValue = remember { mutableStateOf<Boolean>(true) }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.size(104.dp)
        ) {
            imageUrl?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }

                bitmap.value?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Gallery Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(800.dp)
                            .clip(CircleShape)
                    )
                }
            }

            if(imageVectorValue.value == true)
            {
                Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "registration icon",
                modifier = Modifier.fillMaxSize()

            )}
            IconButton(
                onClick = { launcher.launch("image/*")
                          imageVectorValue.value = false},
                modifier = Modifier
                    .background(color = MaterialTheme.colors.primary)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "registration icon",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.height(104.dp)
        ) {
            Text(
                "Dušan",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                "Petrović",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium)
            )
        }
    }



}
