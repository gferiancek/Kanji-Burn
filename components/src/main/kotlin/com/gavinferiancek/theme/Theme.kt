package com.gavinferiancek.kanjiburn.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.gavinferiancek.theme.*
import javax.annotation.concurrent.Immutable

@Immutable
data class KanjiBurnColors(
    val material: Colors,
    val radical: Color,
    val kanji: Color,
    val vocab: Color,
    val success: Color,
) {
    val primary: Color get() = material.primary
    val primaryVariant: Color get() = material.primaryVariant
    val secondary: Color get() = material.secondary
    val secondaryVariant: Color get() = material.secondaryVariant
    val background: Color get() = material.background
    val surface: Color get() = material.surface
    val error: Color get() = material.error
    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val onError: Color get() = material.onError
    val isLight: Boolean get() = material.isLight
}

val LocalKanjiBurnColors = staticCompositionLocalOf {
    KanjiBurnColors(
        material = Colors(
            primary = Color.Unspecified,
            primaryVariant = Color.Unspecified,
            onPrimary = Color.Unspecified,
            secondary = Color.Unspecified,
            secondaryVariant = Color.Unspecified,
            onSecondary = Color.Unspecified,
            background = Color.Unspecified,
            onBackground = Color.Unspecified,
            surface = Color.Unspecified,
            onSurface = Color.Unspecified,
            error = Color.Unspecified,
            onError = Color.Unspecified,
            isLight = true,
        ),
        radical = Color.Unspecified,
        kanji = Color.Unspecified,
        vocab = Color.Unspecified,
        success = Color.Unspecified
    )
}

private val DarkColorPalette = KanjiBurnColors(
    material = darkColors(
        primary = Purple200,
        primaryVariant = Red700,
        onPrimary = white,
        secondary = Teal200,
        background = black,
        surface = darkGray,
        onSurface = white
    ),
    radical = RadicalBackgroundColor,
    kanji = KanjiBackgroundColor,
    vocab = VocabBackgroundColor,
    success = Green
)

private val LightColorPalette = KanjiBurnColors(
    material = lightColors(
        primary = Red500,
        primaryVariant = Red700,
        onPrimary = white,
        secondary = Teal200,
        onSecondary = black,
        background = lightGray,
        onBackground = black,
        surface = white,
        onSurface = black
    ),
    radical = RadicalBackgroundColor,
    kanji = KanjiBackgroundColor,
    vocab = VocabBackgroundColor,
    success = Green,
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

    CompositionLocalProvider(
        LocalKanjiBurnColors provides colors
    ) {
        MaterialTheme(
            colors = colors.material,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object KanjiBurnTheme {
    val colors: KanjiBurnColors
        @Composable
        get() = LocalKanjiBurnColors.current
}