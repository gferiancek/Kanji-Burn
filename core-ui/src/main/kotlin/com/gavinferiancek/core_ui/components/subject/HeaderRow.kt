package com.gavinferiancek.core_ui.components.subject

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gavinferiancek.core_ui.theme.spacing

/**
 * Composable used to display headers at the top of Meaning/ReadingBodys. Text is displayed along with
 * the header in a row.
 *
 * @param header The text for the header
 * @param text The text displayed as the value for the header
 * @param headerTextColor Defaults to Grey, following WaniKani design standards. In the case of Kanji,
 * there will be up to 3 different headers for each type of reading. (Kun'yomi, On'yomi, Nanori) Only one type
 * is considered the Primary Reading, which we would provide a non Grey color as a visual indication of being
 * Primary.
 * @param textColor Defaults to onSurface. As with the Header, Kanji will have three different types of readings.
 * For non primary readings headers, we supply a grey color so that it matches the Header and draws emphasis
 * away from it.
 */
@Composable
fun HeaderRow(
    header: String,
    text: String,
    headerTextColor: Color = Color.Gray,
    textColor: Color = MaterialTheme.colors.onSurface,
) {
    if (text.isNotBlank()) {
        Row {
            Text(
                modifier = Modifier
                    .padding(
                        end = MaterialTheme.spacing.small,
                    )
                    .alignByBaseline(),
                text = header,
                color = headerTextColor,
                style = MaterialTheme.typography.caption,
            )
            Text(
                modifier = Modifier
                    .alignByBaseline(),
                text = text,
                style = MaterialTheme.typography.body2,
                color = textColor,
            )
        }
    }
}