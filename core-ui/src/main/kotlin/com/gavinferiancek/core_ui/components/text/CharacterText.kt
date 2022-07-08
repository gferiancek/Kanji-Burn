package com.gavinferiancek.core_ui.components.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter


/**
 * Composable used throughout the app anytime we want to display a [com.gavinferiancek.core_domain.subject.Subject]'s
 * characters value.
 *
 * Some radicals in the API do not have an ASCII form, and in that case we have to fall back to displaying
 * an image. To avoid constant type checking throughout the app, this composable is built to support both
 * Text and Image display based on whether [characters] is a link.
 */
@ExperimentalCoilApi
@Composable
fun CharacterText(
    characters: String,
    style: TextStyle = MaterialTheme.typography.h2,
    imageLoader: ImageLoader
) {
    if (characters.contains("https")) {
        Image(
            modifier = Modifier
                // Using 1.5x the fontSize seems to be the magic number for making the image
                // take up the same amount of space as the text. This is slightly hacky, but I find
                // it better than hardcoding a fixed height for the list item and also lets us reuse
                // this same composable with different fontSizes.
                .height((style.fontSize.value * 1.5).dp),
            painter = rememberImagePainter(
                data = characters,
                imageLoader = imageLoader,
            ),
            contentDescription = "Picture of Radical, no ASCII form exists",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
        )
    } else {
        Text(
            text = characters,
            color = MaterialTheme.colors.onPrimary,
            style = style,
        )
    }
}