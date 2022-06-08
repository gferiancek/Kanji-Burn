package com.gavinferiancek.core_ui.components.subject

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.subject.*
import com.gavinferiancek.core_ui.theme.*
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun SubjectListItem(
    subject: Subject,
    imageLoader: ImageLoader,
    onSelectSubject: (Int) -> Unit,
) {
    var backgroundColor = Color.Unspecified
    var paintColor = Color.Unspecified
    when (subject) {
        is Radical -> {
            backgroundColor = MaterialTheme.colors.radical
            paintColor = MaterialTheme.colors.radicalLight
        }
        is Kanji -> {
            backgroundColor = MaterialTheme.colors.kanji
            paintColor = MaterialTheme.colors.kanjiLight
        }
        is Vocab -> {
            backgroundColor = MaterialTheme.colors.vocab
            paintColor = MaterialTheme.colors.vocabLight
        }
    }
    Card(
        modifier = Modifier
            .padding(MaterialTheme.spacing.listItemPadding),
        backgroundColor = backgroundColor,
        shape = MaterialTheme.shapes.small,
        onClick = { onSelectSubject(subject.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    /**
                     * Took and slightly modified the code from https://stackoverflow.com/a/69997438
                     * to draw diagonal lines across the entire canvas. I plan to learn more about Canvas,
                     * but it is currently low on my priority list. Will revisit this and try to do
                     * my own implementation later if I have time. As for now, this gets the job done.
                     */
                    if (!subject.unlocked) {
                        val step = 5.dp
                        val angle = 45f
                        val stepPx = step.toPx()
                        val stepCount = (size.width / stepPx).roundToInt()
                        val actualStep = size.width / stepCount
                        val dotSize = Size(width = actualStep / 3, height = size.height * 2)
                        for (i in -15..stepCount + 14) {
                            val rect = Rect(
                                offset = Offset(
                                    x = i * actualStep,
                                    y = (size.height - dotSize.height) / 2
                                ),
                                size = dotSize,
                            )
                            rotate(angle, pivot = rect.center) {
                                drawRect(
                                    color = paintColor,
                                    topLeft = rect.topLeft,
                                    size = rect.size
                                )
                            }
                        }
                    }
                }
                .padding(MaterialTheme.spacing.extraSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Level ${subject.level}",
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.overline,
                textAlign = TextAlign.Start,
            )
            when (subject) {
                is Radical, is Kanji -> {
                    CharacterText(
                        characters = subject.characters,
                        imageLoader = imageLoader
                    )
                    if (subject is Kanji) ReadingText(reading = subject.readings.getPrimaryReading())
                    MeaningText(meaning = subject.meanings.getPrimaryMeaning())
                }
                is Vocab -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = MaterialTheme.spacing.small,
                                end = MaterialTheme.spacing.small,
                                bottom = MaterialTheme.spacing.extraSmall,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CharacterText(
                            characters = subject.characters,
                            imageLoader = imageLoader
                        )
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            ReadingText(reading = subject.readings.getPrimaryReading())
                            MeaningText(meaning = subject.meanings.getPrimaryMeaning())
                        }
                    }
                }
            }
        }
    }
}