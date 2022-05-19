package com.gavinferiancek.core_ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFFA100F1)
val Purple700 = Color(0xFF6A00BD)
val Teal200 = Color(0xFF03DAC5)
val white = Color(0xFFFFFFFF)
val darkGray = Color(0xFF222222)
val black = Color(0xFF000000)
val lightGray = Color(0xFFF2F2F2)

// Since we only need a few extra colors, it's simpler to override
// MaterialTheme.colors instead of providing our own CompositionLocal
val Colors.radical: Color
    get() = Color(0xFF00A1FA)
val Colors.radicalLight: Color
    get() = Color(0xFF75C5ED)
val Colors.kanji: Color
    get() = Color(0xFFF100A1)
val Colors.kanjiLight: Color
    get() = Color(0xFFED75C5)
val Colors.vocab: Color
    get() = Color(0xFFA100F1)
val Colors.vocabLight: Color
    get() = Color(0xFFC575ED)
val Colors.accept: Color
    get() = Color(0xFF009a34)
val Colors.hintBox: Color
    get() = Color(0xFF222222)