package com.gavinferiancek.ui_subjectdetail.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.components.RadicalCharacterSelector
import com.gavinferiancek.subject_domain.Subject

@ExperimentalCoilApi
@Composable
fun SubjectDetailHeader(
    subject: Subject,
    color: Color,
    imageLoader: ImageLoader,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.20f),
        color = color,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            when (subject) {
                is Subject.Radical -> {
                    RadicalCharacterSelector(
                        characters = subject.getRadicalCharacters(),
                        textStyle = MaterialTheme.typography.h1,
                        imageLoader = imageLoader,
                    )
                    Text(
                        text = subject.getPrimaryMeaning(),
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                    )
                }
                else -> {
                    Text(
                        text = subject.characters,
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.onPrimary,
                    )
                    Text(
                        text = subject.getPrimaryReading(),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onPrimary,
                    )
                    Text(
                        text = subject.getPrimaryMeaning(),
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                    )
                }
            }
        }
    }
}
