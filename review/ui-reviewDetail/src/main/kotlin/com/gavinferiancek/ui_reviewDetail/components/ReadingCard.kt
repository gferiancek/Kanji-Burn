package com.gavinferiancek.ui_reviewDetail.components

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.gavinferiancek.core_domain.subject.PronunciationAudio
import com.gavinferiancek.core_ui.components.TitledCardView
import com.gavinferiancek.core_ui.theme.spacing
import com.gavinferiancek.ui_reviewDetail.R
import com.gavinferiancek.ui_reviewDetail.util.generateAnnotatedString

@Composable
fun ReadingCard(
    title: String,
    mediaPlayer: MediaPlayer? = null,
    onyomiReading: String = "",
    kunyomiReading: String = "",
    nanoriReading: String = "",
    primaryReading: String = "",
    pronunciationAudios: List<PronunciationAudio> = listOf(),
    readingText: String,
    readingHint: String = "",
) {
    TitledCardView(
        title = title,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            if (primaryReading.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = primaryReading,
                        style = MaterialTheme.typography.h4
                    )
                    val context = LocalContext.current
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        pronunciationAudios.forEach { pronunciationAudio ->
                            IconButton(
                                onClick = {
                                    mediaPlayer?.apply {
                                        reset()
                                        setDataSource(
                                            context,
                                            Uri.parse(pronunciationAudio.url)
                                        )
                                        prepareAsync()
                                    }
                                },
                            ) {
                                Row {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_volume_mute_12),
                                        contentDescription = "Icon for audio player",
                                        tint = MaterialTheme.colors.onSurface,
                                    )
                                    val metadata = pronunciationAudio.metadata
                                    Text(
                                        text = metadata.name.uppercase(),
                                        style = MaterialTheme.typography.overline,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "(${metadata.description.uppercase()}, ${metadata.gender.uppercase()})",
                                        style = MaterialTheme.typography.overline,
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                val headerRows = mapOf(
                    "ON'YOMI" to onyomiReading,
                    "KUN'YOMI" to kunyomiReading,
                    "NANORI" to nanoriReading
                )
                headerRows.forEach { (header, text) ->
                    if (text.isNotBlank()) HeaderTextRow(header = header, text = text)
                }
            }
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small),
                text = if (primaryReading.isNotEmpty()) "Explanation" else "Mnemonic",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface,
            )
            val uriHandler = LocalUriHandler.current
            val annotatedReading = generateAnnotatedString(sourceText = readingText)
            ClickableText(
                text = annotatedReading,
                style = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.onSurface,
                ),
                onClick = {
                    annotatedReading.getStringAnnotations(tag = "URL", start = it, end = it)
                        .firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item)
                        }
                },
            )
            // The only markup tag present in readingHint is <ja>, which doesn't require any annotations.
            // No need to run it through generateAnnotatedString as we can just regex the tag out.
            if (readingHint.isNotBlank()) HintBox(readingHint.replace("<.*?>".toRegex(), ""))
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small),
                text = "Note",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface,
            )
            Text(
                text = "Click to add note",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
            )
        }
    }
}
