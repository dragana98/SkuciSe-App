package com.blackbyte.skucise.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.R
import kotlin.math.round

@Composable
fun RatingStars(
    rating: Float,
    maxRating: Int,
    starSize: Dp,
    spacing: Dp,
    tint: Color
) {
    var whole: Int = rating.toInt()
    var decimal: Int = round((rating * 10f) - (whole * 10f)).toInt()

    whole += decimal / 10
    decimal %= 10

    if ((rating < 0f) or (rating > maxRating.toFloat())) {
        whole = 0
        decimal = 0
    }

    Row {
        var t: Int = 0
        var i: Int = 0

        while (i < whole) {
            Icon(
                painter = painterResource(R.drawable.starfilled),
                contentDescription = "star rating",
                tint = tint,
                modifier = Modifier.size(starSize)
            )
            Spacer(modifier = Modifier.size(spacing))
            i++
            t++
        }

        if (decimal != 0) {
            Box {
                Icon(
                    painter = painterResource(R.drawable.staroutlined),
                    contentDescription = "star rating",
                    tint = tint,
                    modifier = Modifier.size(starSize)
                )
                Box(
                    modifier =
                    Modifier
                        .offset(
                            x = starSize
                                .div(10)
                                .times(decimal - 10), y = 0.dp
                        )
                        .clip(shape = RectangleShape)
                ) {
                    Box(
                        modifier = Modifier.offset(
                            x = starSize
                                .div(10)
                                .times(10 - decimal), y = 0.dp
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.starfilled),
                            contentDescription = "star rating",
                            tint = tint,
                            modifier = Modifier.size(starSize)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(spacing))
            t++
        }
        while (t < maxRating) {
            Icon(
                painter = painterResource(R.drawable.staroutlined),
                contentDescription = "star rating",
                tint = tint,
                modifier = Modifier.size(starSize)
            )
            Spacer(modifier = Modifier.size(spacing))
            t++
        }

    }
}