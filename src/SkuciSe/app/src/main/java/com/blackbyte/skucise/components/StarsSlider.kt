package com.blackbyte.skucise.components


import com.google.android.material.slider.Slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.data.listOfCities
import com.blackbyte.skucise.data.listOfObjects
import com.blackbyte.skucise.ui.theme.Purple200
import com.blackbyte.skucise.ui.theme.Purple500
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.data.Star
import com.blackbyte.skucise.ui.theme.Gold

@Stable
@Composable
fun StarsSlider(
    step: Int,
    stars: List<Star>
    //value: Float,
    //onValueChanged: (value: Int) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(0f) }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stars[sliderPosition.toInt()].values,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "star",
            modifier = Modifier.size(19.dp),
            tint = Gold
        )
    }
    Slider(
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        valueRange = 0f..5f,
        onValueChangeFinished = {
            // launch some business logic update with the state you hold
            // viewModel.updateSelectedSliderValue(sliderPosition)
        },
        steps = step,
        colors = SliderDefaults.colors(
            thumbColor = Purple200,
            activeTrackColor = Purple200
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}

