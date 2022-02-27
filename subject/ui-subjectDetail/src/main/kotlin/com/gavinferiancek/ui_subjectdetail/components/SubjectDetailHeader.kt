package com.gavinferiancek.ui_subjectdetail.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.gavinferiancek.subject_domain.Kanji
import com.gavinferiancek.subject_domain.Radical
import com.gavinferiancek.subject_domain.Vocab
import com.gavinferiancek.ui_subjectdetail.ui.SubjectDetailState

@ExperimentalCoilApi
@Composable
fun SubjectDetailHeader(
    state: SubjectDetailState,
    imageLoader: ImageLoader,
) {
    state.subject?.let {
        val backgroundColor = when (it) {
            is Radical -> KanjiBurnTheme.colors.radical
            is Kanji -> KanjiBurnTheme.colors.kanji
            is Vocab -> KanjiBurnTheme.colors.vocab
            else -> KanjiBurnTheme.colors.radical
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.20f),
            color = backgroundColor,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Log.d("TypeConverterTest", state.subject.toString())
                when (state.subject) {
                    is Radical -> {
                        if (state.subject.characters.isNotBlank()) {
                            Text(
                                text = state.subject.characters,
                                color = KanjiBurnTheme.colors.onPrimary,
                                fontSize = 48.sp,
                            )

                        } else {
                            Image(
                                modifier = Modifier
                                    .height(64.dp),
                                painter = rememberImagePainter(
                                    data = state.subject.characterImage,
                                    imageLoader = imageLoader,
                                ),
                                contentDescription = "Picture of Radical, no ASCII form exists",
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(KanjiBurnTheme.colors.onPrimary)
                            )
                        }
                        Text(
                            text = state.subject.getPrimaryMeaning(),
                            fontSize = 18.sp,
                            color = KanjiBurnTheme.colors.onPrimary,
                        )
                    }
                    else -> {
                        Text(
                            text = state.subject.characters,
                            fontSize = 48.sp,
                            color = KanjiBurnTheme.colors.onPrimary,
                        )
                        Text(
                            text = state.subject.getPrimaryReading(),
                            fontSize = 22.sp,
                            color = KanjiBurnTheme.colors.onPrimary,
                        )
                        Text(
                            text = state.subject.getPrimaryMeaning(),
                            fontSize = 18.sp,
                            color = KanjiBurnTheme.colors.onPrimary,
                        )
                    }
                }
            }
        }
    }
}