package com.example.projectserotonin.ui.theme

import androidx.compose.ui.graphics.Color



val color_light_primary = Color(0xFFEB252D)
val color_disabled_grey = Color(0xFFE6E6E6)
val color_grey_text_label = Color(0xFF7E7E7E)
val color_grey_hint = Color(0xFFB2B2B2)
val color_grey_low_light = Color(0xFFF5F5F5)
val color_black_text_color = Color(0xFF151515)
val color_green_text = Color(0xFF0F9D82)
val color_brown_text = Color(0xFFD59C43)

data class Colors(
    val primary: Color,
    val secondary: Color,
    val contentPrimary: Color,
    val contentSecondary: Color,
    val contentTertiary: Color,
    val contentBorder: Color,
    val onPrimary: Color,

    val success: Color,
    val brown: Color,
    val pink: Color,
    val blue: Color,
    val green: Color,
    val toastBackgroundColor: Color,
    val trackColor: Color,
    val blueSky: Color,
    val dullWhite: Color,
    val darkblue: Color
)

val LightColors = Colors(
    primary = color_light_primary,
    secondary = color_grey_low_light,
    contentPrimary = color_black_text_color,
    contentSecondary = color_grey_text_label,
    contentTertiary = color_grey_hint,
    contentBorder = color_disabled_grey,
    onPrimary = Color(0xFFFFFFFF),
    success = Color(0xFF41BE88),
    brown  = color_brown_text,
    pink = Color(0xFFFFD0CC),
    blue = Color(0xFF0000FF),
    green = color_green_text,
    toastBackgroundColor = Color(0XFF7F00FF),
    trackColor = Color(0X50151515),
    blueSky = Color(0XFF73D7FF),
    dullWhite = Color(0XFFEDEADE),
    darkblue = Color(0XFF00008B)
    )




