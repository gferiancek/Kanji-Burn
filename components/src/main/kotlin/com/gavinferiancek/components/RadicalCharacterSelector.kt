package com.gavinferiancek.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.gavinferiancek.theme.spacing


/**
 * Some radicals in the API do not have an ASCII form, and in that case we have to fall back to displaying
 * an image.  Since this check has to be done ANYTIME we want to display a radical's characters, the
 * logic has been extracted out to the components module.
 */
@ExperimentalCoilApi
@Composable
fun RadicalCharacterSelector(
    characters: String,
    textStyle: TextStyle,
    imageLoader: ImageLoader
) {
    // All radicals are a single character, so if the length > 1 we know we have an image url.
    if (characters.length == 1) {
        Text(
            text = characters,
            color = MaterialTheme.colors.onPrimary,
            style = textStyle,
        )
    } else {
        Image(
            modifier = Modifier
                // Using 1.5x the fontSize seems to be the magic number for making the image
                // take up the same amount of space as the text. This is slightly hacky, but I find
                // it better than hardcoding a fixed height for the list item and also lets us reuse
                // this same composable with different fontSizes.
                .height((textStyle.fontSize.value * 1.5).dp)
                .padding(MaterialTheme.spacing.small),
            painter = rememberImagePainter(
                data = characters,
                imageLoader = imageLoader,
            ),
            contentDescription = "Picture of Radical, no ASCII form exists",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
        )
    }
}