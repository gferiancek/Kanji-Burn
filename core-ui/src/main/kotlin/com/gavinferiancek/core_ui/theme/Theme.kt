package com.gavinferiancek.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable

/**
 * NOTE: Even though the theme folder is typically stored in the app module, we have moved it into
 * the ui-components module.  Since this is a multi-module project, we wouldn't have access to spacing
 * or our custom colors unless we implemented the app module, and that defeats the entire purpose of
 * a multi-module setup.  Our app module already implements ui-components, so it can still access these
 * values.
 */
private val DarkColorPalette = darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        onPrimary = white,
        secondary = Teal200,
        background = black,
        surface = darkGray,
        onSurface = white,
    )

private val LightColorPalette = lightColors(
        primary = Purple500,
        primaryVariant = Purple700,
        onPrimary = white,
        secondary = Teal200,
        onSecondary = black,
        background = lightGray,
        onBackground = black,
        surface = white,
        onSurface = black
    )
@Composable
fun KanjiBurnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = NotoSansTypography,
        shapes = Shapes,
        content = content
    )
}