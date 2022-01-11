package com.gavinferiancek.ui_subjectlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.gavinferiancek.subject_domain.Kanji
import com.gavinferiancek.subject_domain.Radical
import com.gavinferiancek.subject_domain.Subject
import com.gavinferiancek.subject_domain.Vocab

@ExperimentalCoilApi
@Composable
fun SubjectListItem(
    subject: Subject,
    imageLoader: ImageLoader,
) {
    val backgroundColor = when(subject) {
        is Radical -> KanjiBurnTheme.colors.radical
        is Kanji -> KanjiBurnTheme.colors.kanji
        else -> KanjiBurnTheme.colors.vocab
    }
    val textColor = KanjiBurnTheme.colors.onPrimary
    Card(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 2.dp,
                    ),
                text = "Level ${subject.level}",
                color = textColor,
                fontSize = 10.sp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                when (subject) {
                    is Radical -> {
                        val characters = subject.characters

                        if (characters != null) {
                            Text(
                                text = characters,
                                color = textColor,
                                fontSize = 36.sp,
                            )
                        } else {
                            Image(
                                modifier = Modifier
                                    .height(48.dp),
                                painter = rememberImagePainter(
                                    data = subject.characterImage,
                                    imageLoader = imageLoader,
                                ),
                                contentDescription = "Picture of Radical, no ASCII form exists",
                                contentScale = ContentScale.Inside,
                                colorFilter = ColorFilter.tint(textColor)
                            )
                        }
                        Text(
                            text = subject.getPrimaryMeaning(),
                            color = textColor,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    is Kanji -> {
                        Text(
                            text = subject.characters,
                            color = textColor,
                            fontSize = 36.sp
                        )
                        Text(
                            text = subject.getPrimaryReading(),
                            color = textColor,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = subject.getPrimaryMeaning(),
                            color = textColor,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    is Vocab -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    bottom = 2.dp,
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = subject.characters,
                                color = textColor,
                                fontSize = 36.sp
                            )
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = subject.getPrimaryReading(),
                                    color = textColor,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = subject.getPrimaryMeaning(),
                                    color = textColor,
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