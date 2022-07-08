package com.gavinferiancek.core_ui.components.subject

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import com.gavinferiancek.core_domain.state.StudyMaterialsEditState
import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_domain.subject.*
import com.gavinferiancek.core_ui.components.text.AnnotatedText
import com.gavinferiancek.core_ui.R

/**
 * Composable card that shows info about the reading of the subject.
 *
 * @param color Color for subject type used in theming.
 * @param mediaPlayer MediaPlayer object passed down from Activity. Ensures only one instance is used
 * throughout the app.
 * @param studyMaterials StudyMaterials object that contains user entered info. (Notes, synonyms.)
 * @param subject Subject for the given page. Opted to pass in the whole subject instead of required fields
 * as it severely cuts down on parameters and the subject shouldn't change so no unnecessary recompositions will
 * be called.
 * @param editState ReviewDetailEditState sealed class used to determine what type of edit we are doing. (Options
 * are Meaning Note, Reading Note, Adding a Synonym, and Deleting a Synonym.)
 * @param textFieldValue TextFieldValue is required so that we can edit the cursor position in the event that
 * a Meaning/Reading note already exists. (We move the cursor to the end of the text.) Stored in State.
 * @param updateStudyMaterials Event to Post/Put updated StudyMaterials to Wanikani Server.
 * @param updateDetailEditState Event to update states ReviewDetailEditState.
 * @param updateTextFieldValue Event to update states TextFieldValue.
 */
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ReadingBody(
    color: Color,
    mediaPlayer: MediaPlayer?,
    studyMaterials: StudyMaterials,
    subject: Subject,
    editState: StudyMaterialsEditState,
    textFieldValue: TextFieldValue,
    updateStudyMaterials: (String) -> Unit,
    updateDetailEditState: (StudyMaterialsEditState) -> Unit,
    updateTextFieldValue: (TextFieldValue) -> Unit,
) {
    Column {
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
                        HeaderRow(
                            header = header,
                            text = reading.reading,
                            headerTextColor = textColor,
                            textColor = textColor,
                        )
                    }
                }
            }
            AnnotatedText(sourceText = subject.readingMnemonic)
            if (subject is Kanji) {
                HintBox(subject.readingHint)
            }
            UserNote(
                userNote = studyMaterials.readingNote,
                textFieldValue = textFieldValue,
                color = color,
                isEditing = editState is StudyMaterialsEditState.EditingReadingNote,
                targetEditState = StudyMaterialsEditState.EditingReadingNote,
                updateDetailEditState = updateDetailEditState,
                updateStudyMaterials = updateStudyMaterials,
                updateTextFieldValue = updateTextFieldValue,
            )
        }
    }
