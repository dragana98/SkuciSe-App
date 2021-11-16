package com.blackbyte.skucise.components


import androidx.compose.animation.animateColorAsState
import androidx.compose.ui.platform.LocalContext
import com.blackbyte.skucise.data.Filter
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.components.OutlinedInputField
import com.blackbyte.skucise.components.OutlinedPasswordField
import com.blackbyte.skucise.components.SearchField
import com.blackbyte.skucise.data.listOfCities
import com.blackbyte.skucise.ui.theme.Purple200
import com.blackbyte.skucise.ui.theme.Purple500
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import kotlin.math.ln

@Composable
fun AdvertiseFilterChip(
    filters:List<Filter>,
    clickedValueB: MutableState<String>
){
    val context = LocalContext.current
    var previousFun by remember {
        mutableStateOf(fun(): Unit{})
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ){

        Column(
            modifier = Modifier
                .padding(5.dp),
            horizontalAlignment = Alignment.Start
        ){
            FlowRow(
                // position of chips
                mainAxisAlignment = FlowMainAxisAlignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 16.dp)
                    .padding(horizontal = 5.dp)
            ){

                filters.forEach{filter ->

                    AdvertiseChip(
                        filter = filter,
                        modifier = Modifier.padding(end = 4.dp,bottom = 8.dp),
                        onToggle = {t ->  previousFun = t},
                        previousFun = previousFun,
                        clickedValue = clickedValueB
                    )

                    Spacer(modifier = Modifier.size(5.dp))
                }
            }
        }
    }
}

@Composable
fun AdvertiseChip(
    filter: Filter,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    onToggle: (()->Unit) -> Unit,
    previousFun: () -> Unit,
    clickedValue: MutableState<String>
){
    var selected by remember { mutableStateOf(filter.enabled)}

    val disableDecoration = fun(){
        selected.value = false
    }
    val setSelected = fun(value: Boolean){

        selected.value = value
        clickedValue.value = filter.name

        previousFun()
        onToggle(disableDecoration)
    }

    val backgroundColor by  animateColorAsState(
        if(selected.value) Purple500 else Color.White
    )
    val border = Modifier.fadeInDiagonalGradientBorder(
        showBorder = !selected.value,
        colors = listOf(
            Purple500, Purple200
        ),
        shape = shape,

        )
    val textColor by animateColorAsState(
        if(selected.value) Color.White else
            Color.Black
    )

    // surface
    AdvertiseChipSurface(
        modifier = modifier.height(40.dp),
        color = backgroundColor,
        contentColor = textColor,
        shape = shape,
        elevation = 2.dp
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        val pressed by interactionSource.collectIsPressedAsState()
        val backgroundPressed =
            if (pressed) {
                Modifier.offsetGradientBackground(
                    listOf(
                        Purple500, Purple200
                    ),
                    200f,
                    0f
                )
            } else {
                Modifier.background(Color.Transparent)
            }
        Box(
            modifier = Modifier
                .toggleable(
                    value = selected.value,
                    onValueChange = setSelected,
                    interactionSource = interactionSource,
                    indication = null
                )
                .then(backgroundPressed)
                .then(border)
                .height(40.dp)

        ) {
            Text(
                text = filter.name,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun AdvertiseChipSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = Purple500,
    contentColor: Color = Purple200,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape, clip = false)
            .zIndex(elevation.value)
            .then(if (border != null) Modifier.border(border, shape = shape) else Modifier)
            .background(
                color = getBackgroundColorForElevation(color = color, elevation = elevation),
                shape = shape
            )
            .clip(shape = shape)

    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}

@Composable
private fun getBackgroundColorForElevation(color: Color, elevation: Dp) : Color {
    return if (elevation > 0.dp) {
        color.withElevation(elevation = elevation)
    } else {
        color
    }
}

private fun calculateForeground(elevation: Dp) : Color {
    val alpha = ((4.5f * ln(elevation.value + 1)) + 2f) / 100f
    return Color.White.copy(alpha = alpha)
}

private fun Color.withElevation(elevation: Dp): Color {
    val foreground = calculateForeground(elevation = elevation)
    return foreground.compositeOver(this)
}
