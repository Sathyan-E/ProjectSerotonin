package com.example.projectserotonin.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

private val localDimens = staticCompositionLocalOf { Dimens() }
private val localColorScheme = staticCompositionLocalOf { LightColors }
private val localTypography = staticCompositionLocalOf { Typography() }

object SRTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = localColorScheme.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = localTypography.current

    val dimens: Dimens
        @Composable
        @ReadOnlyComposable
        get() = localDimens.current
}

@Composable
fun SRTheme(
    content: @Composable () -> Unit,
) {

    // TODO: Add Support for Dark Mode
    val colorScheme = LightColors

    val typography = Typography(
        displayLarge = displayLarge(),
        displayMedium = displayMedium(),
        displaySmall = displaySmall(),
        headlineLarge = headlineLarge(),
        headlineMedium = headlineMedium(),
        headlineSmall = headlineSmall(),
        titleLarge = titleLarge(),
        titleMedium = titleMedium(),
        titleSmall = titleSmall(),
        bodyLarge = bodyLarge(),
        bodyMedium = bodyMedium(),
        bodySmall = bodySmall(),
        labelLarge = labelLarge(),
        labelMedium = labelMedium(),
        labelSmall = labelSmall(),
        contentMedium = contentMedium()
    )

    CompositionLocalProvider(
        localColorScheme provides colorScheme,
        localTypography provides typography,
        localDimens provides Dimens(),
    ) {
        content()
    }
}