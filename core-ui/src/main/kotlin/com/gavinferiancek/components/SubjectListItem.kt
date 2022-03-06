package com.gavinferiancek.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.components.RadicalCharacterSelector
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.theme.kanji
import com.gavinferiancek.theme.radical
import com.gavinferiancek.theme.spacing
import com.gavinferiancek.theme.vocab

@ExperimentalCoilApi
@Composable
fun SubjectListItem(
    subject: Subject,
    imageLoader: ImageLoader,
    onSelectSubject: (Int) -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(MaterialTheme.spacing.listItemPadding)
            .fillMaxWidth()
            .clickable { onSelectSubject(subject.id) },
        elevation = MaterialTheme.spacing.extraSmall,
        shape = MaterialTheme.shapes.small,
        color = when(subject) {
            is Subject.Radical -> MaterialTheme.colors.radical
            is Subject.Kanji -> MaterialTheme.colors.kanji
            is Subject.Vocab -> MaterialTheme.colors.vocab
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.spacing.small,
                        top = MaterialTheme.spacing.extraSmall,
                    ),
                text = "Level ${subject.level}",
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.overline,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.extraSmall),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                when (subject) {
                    is Subject.Radical -> {
                        RadicalCharacterSelector(
                            characters = subject.getRadicalCharacters(),
                            textStyle = MaterialTheme.typography.h2,
                            imageLoader = imageLoader,
                        )
                        Text(
                            text = subject.getPrimaryMeaning(),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.caption,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    is Subject.Kanji -> {
                        Text(
                            text = subject.characters,
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.h2,
                        )
                        Text(
                            text = subject.getPrimaryReading(),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.h4,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = subject.getPrimaryMeaning(),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.caption,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    is Subject.Vocab -> {
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
                            Text(
                                text = subject.characters,
                                color = MaterialTheme.colors.onPrimary,
                                style = MaterialTheme.typography.h2,
                            )
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = subject.getPrimaryReading(),
                                    color = MaterialTheme.colors.onPrimary,
                                    style = MaterialTheme.typography.h4,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = subject.getPrimaryMeaning(),
                                    color = MaterialTheme.colors.onPrimary,
                                    style = MaterialTheme.typography.caption,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}