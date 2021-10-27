package com.blackbyte.skucise.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.R


val WorkSans = FontFamily(
    Font(R.font.worksans_regular, weight = FontWeight.Normal),
    Font(R.font.worksans_medium, weight = FontWeight.Medium),
    Font(R.font.worksans_bold, weight = FontWeight.Bold),
    Font(R.font.worksans_black, weight = FontWeight.Black),

    Font(R.font.worksans_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.worksans_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.worksans_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.worksans_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),

    )


// Set of Material typography styles to start with
val Typography = Typography(defaultFontFamily = WorkSans)

/*
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
     Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

)*/