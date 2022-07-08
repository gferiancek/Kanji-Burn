package com.gavinferiancek.core_ui.components.subject

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.gavinferiancek.core_ui.theme.spacing
import com.gavinferiancek.core_domain.state.StudyMaterialsEditState

/**
 * Composable that shows a Note header, along with the user note for that card. (Meaning or Reading.)
 * Also provides necessary functioning to allow the user to add or edit the displayed user note.
 *
 * @param userNote The note value for the card type. (meaningNote for MeaningCard and readingNote for ReadingCard.)
 * @param textFieldValue We can't directly access a TextField's TextFieldValue to change the cursor position, so we have
 * to provide a new one with the desired parameters. It also doubles as a local cache of the user's changes to userNote, since
 * userNote is only updated after we send the data to and get a successful response from the WaniKani API.
 * @param color Subject color used in styling UI elements.
 * @param isEditing Value of whether or not this UserNote instance is being edited.
 * @param targetEditState The desired DetailEditState of this UserNote. Either DetailEditState.EditingMeaning or
 * DetailEditState.EditingReading. Used in updateDetailEditState to tell the ViewModel which userNote we are editing.
 * @param updateDetailEditState Updates the DetailEditState with the targetEditState. This is used so that only one thing on
 * the detail screen can be edited at once.
 * @param updateStudyMaterials Sends the user entered data back to the ViewModel so it can send the new data to the WaniKani API.
 */
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun UserNote(
    userNote: String,
    textFieldValue: TextFieldValue,
    color: Color,
    isEditing: Boolean,
    targetEditState: StudyMaterialsEditState,
    updateDetailEditState: (StudyMaterialsEditState) -> Unit,
    updateStudyMaterials: (String) -> Unit,
    updateTextFieldValue: (TextFieldValue) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.small),
            text = "Note",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface,
        )
        AnimatedContent(
            targetState = isEditing,
            transitionSpec = {
                if (targetState) {
                    slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Up,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    ) + fadeIn() with slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Up
                    ) + fadeOut()
                } else {
                    slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Down,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMediumLow,
                        )
                    ) with slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Down
                    ) + fadeOut()
                }
            }
        ) { targetState ->
            if (targetState) {
                val maxChar = 500
                LaunchedEffect(Unit) { focusRequester.requestFocus() }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = textFieldValue,
                    onValueChange = { value ->
                        if (value.text.length <= maxChar) updateTextFieldValue(value.copy(text = value.text))
                    },
                    label = {
                        Text(text = "Enter Note (${maxChar - textFieldValue.text.length} characters remaining)")
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = color,
                        focusedLabelColor = color,
                        focusedBorderColor = color,
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                keyboardController?.hide()
                                updateDetailEditState(StudyMaterialsEditState.NotEditing)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Cancel editing and discard changes",
                                tint = MaterialTheme.colors.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            updateStudyMaterials(textFieldValue.text)
                        }
                    )

                )

            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                enabled = true,
                                onClick = {
                                    updateDetailEditState(targetEditState)
                                    val newTextFieldValue = TextFieldValue(
                                        text = userNote,
                                        selection =
                                        if (userNote.isNotEmpty()) {
                                            TextRange(userNote.length)
                                        } else {
                                            TextRange(start = 0, end = 0)
                                        }
                                    )
                                    updateTextFieldValue(newTextFieldValue)
                                }
                            ),
                        text = userNote.ifBlank { "Click to add/edit note" },
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface,
                    )
                }
            }
        }
    }
}