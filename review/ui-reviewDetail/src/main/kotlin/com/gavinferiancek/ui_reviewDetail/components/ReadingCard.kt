package com.gavinferiancek.ui_reviewDetail.components

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_domain.subject.*
import com.gavinferiancek.core_ui.components.TitledCardView
import com.gavinferiancek.core_ui.theme.spacing
import com.gavinferiancek.review_domain.DetailEditState
import com.gavinferiancek.ui_reviewDetail.R
import com.gavinferiancek.ui_reviewDetail.util.generateAnnotatedString

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun ReadingCard(
    color: Color,
    title: String,
    mediaPlayer: MediaPlayer?,
    studyMaterials: StudyMaterials,
    subject: Subject,
    editState: DetailEditState,
    textFieldValue: TextFieldValue,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    updateStudyMaterials: (String) -> Unit,
    updateDetailEditState: (DetailEditState) -> Unit,
    updateTextFieldValue: (TextFieldValue) -> Unit,
) {
    TitledCardView(
        title = title,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            if (subject is Vocab) {
                val context = LocalContext.current
                Text(
                    text = subject.readings.getPrimaryReading(),
                    style = MaterialTheme.typography.h4
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    subject.pronunciationAudios.forEach { pronunciationAudio ->
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
            if (subject is Kanji) {
                val headerRows = mapOf(
                    "ON'YOMI" to subject.readings.filterReadingsByType("onyomi"),
                    "KUN'YOMI" to subject.readings.filterReadingsByType("kunyomi"),
                    "NANORI" to subject.readings.filterReadingsByType("nanori")
                )
                headerRows.forEach { (header, reading) ->
                    if (reading.reading.isNotBlank()) {
                        val textColor =
                            if (reading.primary) MaterialTheme.colors.onSurface
                            else Color.Gray

                        Row {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = MaterialTheme.spacing.small,
                                    )
                                    .alignByBaseline(),
                                text = header,
                                color = textColor,
                                style = MaterialTheme.typography.caption,
                            )
                            Text(
                                modifier = Modifier
                                    .alignByBaseline(),
                                text = reading.reading,
                                style = MaterialTheme.typography.body2,
                                color = textColor,
                            )
                        }
                    }
                }
            }

            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small),
                text = if (subject is Vocab) "Explanation" else "Mnemonic",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface,
            )

            val uriHandler = LocalUriHandler.current
            val annotatedReading = generateAnnotatedString(sourceText = subject.readingMnemonic)
            ClickableText(
                text = annotatedReading,
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onSurface,
                ),
                onClick = {
                    annotatedReading.getStringAnnotations(tag = "URL", start = it, end = it)
                        .firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item)
                        }
                },
            )
            if (subject is Kanji) {
                // The only markup tag present in readingHint is <ja>, which doesn't require any annotations.
                // No need to run it through generateAnnotatedString as we can just regex the tag out.
                if (subject.readingHint.isNotBlank()) HintBox(
                    subject.readingHint.replace(
                        "<.*?>".toRegex(),
                        ""
                    )
                )
            }

            UserNote(
                userNote = studyMaterials.readingNote,
                textFieldValue = textFieldValue,
                color = color,
                isEditing = editState is DetailEditState.EditingReading,
                targetEditState = DetailEditState.EditingReading,
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                updateDetailEditState = updateDetailEditState,
                updateStudyMaterials = updateStudyMaterials,
                updateTextFieldValue = updateTextFieldValue,
            )
        }
    }
}
