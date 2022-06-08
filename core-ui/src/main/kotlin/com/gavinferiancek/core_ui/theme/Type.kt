package com.gavinferiancek.core_ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gavinferiancek.core_ui.R

private val NotoSans = FontFamily(
    Font(R.font.notosans_light, weight = FontWeight.Light),
    Font(R.font.notosans_regular, weight = FontWeight.Normal),
    Font(R.font.notosans_italic, style = FontStyle.Italic),
    Font(R.font.notosans_bold, weight = FontWeight.Bold),
)
// Set of Material typography styles to start with
val NotoSansTypography = Typography(
    h1 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Light,
        fontSize = 48.sp,
    ),
    h2 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Light,
        fontSize = 36.sp
    ),
    h3 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Normal,
        fontSize =  24.sp
    ),
    h4 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    h5 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    button = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    caption = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    ),
    overline = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
)