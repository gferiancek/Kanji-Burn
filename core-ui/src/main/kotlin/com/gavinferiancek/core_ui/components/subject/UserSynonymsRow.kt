package com.gavinferiancek.core_ui.components.subject

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gavinferiancek.core_ui.theme.spacing
import com.gavinferiancek.core_domain.state.StudyMaterialsEditState
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val NO_SELECTION = -1

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun UserSynonymsRow(
    meaningSynonyms: List<String>,
    color: Color,
    scope: CoroutineScope,
    lazyState: LazyListState,
    isEditing: Boolean,
    isDeleting: Boolean,
    updateStudyMaterials: (String) -> Unit,
    updateDetailEditState: (StudyMaterialsEditState) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        FlowRow(
            mainAxisSize = SizeMode.Expand,
        ) {
            val selectedIndex = remember { mutableStateOf(NO_SELECTION) }
            if (!isDeleting) selectedIndex.value = NO_SELECTION

            meaningSynonyms.forEachIndexed { index, synonym ->
                Chip(
                    modifier = Modifier
                        .padding(
                            top = 0.dp,
                            end = MaterialTheme.spacing.small
                        ),
                    border = BorderStroke(
                        width = ChipDefaults.OutlinedBorderSize,
                        color = color,
                    ),
                    colors = ChipDefaults.outlinedChipColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = color,
                    ),
                    onClick = {
                        when (selectedIndex.value) {
                            index -> {
                                selectedIndex.value = NO_SELECTION
                                updateDetailEditState(StudyMaterialsEditState.NotEditing)
                            }
                            else -> {
                                selectedIndex.value = index
                                updateDetailEditState(StudyMaterialsEditState.DeletingUserSynonym)
                            }
                        }
                    }
                ) {
                    Text(
                        text = synonym,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface
                    )
                    AnimatedVisibility(
                        visible = isDeleting && selectedIndex.value == index,
                        enter = expandHorizontally(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMediumLow,
                            )
                        ),
                        exit = shrinkHorizontally(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMediumLow,
                            )
                        ),
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(start = MaterialTheme.spacing.extraSmall)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = LocalIndication.current,
                                    enabled = true,
                                    onClick = { updateStudyMaterials(synonym) },
                                ),
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Button to remove synonym",
                            tint = MaterialTheme.colors.error,
                        )
                    }
                }
            }
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = color,
                ),
                onClick = { updateDetailEditState(StudyMaterialsEditState.EditingUserSynonym) }
            ) {
                Text(
                    text = "+ ADD SYNONYM",
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        AnimatedVisibility(
            visible = isEditing,
            enter = expandVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                )
            ) + fadeIn(),
        ) {
            val text = remember { mutableStateOf("") }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusEvent {
                        if (it.isFocused) {
                            scope.launch {
                                // If totalItemsCount > 3 then were are in a Kanji/Vocab where the first
                                // item is NOT a MeaningCard. This causes the softKeyboard to hide the
                                // TextField upon opening if the first item is visible. MeaningCard is always
                                // index 1 for Kanji/Vocab so we can scroll to it to avoid the issue.
                                if (lazyState.firstVisibleItemIndex == 0 && lazyState.layoutInfo.totalItemsCount > 3) {
                                    lazyState.animateScrollToItem(1)
                                }
                            }
                        }
                    },
                value = text.value,
                onValueChange = { value ->
                    text.value = value
                },
                label = {
                    Text(text = "Enter new synonym")
                },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onSurface,
                    focusedBorderColor = color,
                    focusedLabelColor = color,
                    cursorColor = color,
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                            updateDetailEditState(StudyMaterialsEditState.NotEditing)
                        }
                    ) {
                        Column {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Cancel editing and discard changes",
                                tint = MaterialTheme.colors.error
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        updateStudyMaterials(text.value)
                    }
                ),
            )
        }
    }
}