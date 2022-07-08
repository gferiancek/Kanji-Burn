package com.gavinferiancek.core_ui.components.subject

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_ui.theme.spacing

/**
 * Composable that builds a GridList row by row for scenarios where we want to draw a subject list
 * inside of a LazyColumn. (Such as the ReviewDetailScreen). Takes connections and windows them based
 * on numberOfColumns and draws each row one by one.
 *
 * @param numberOfColumns Specifies the number of columns to include in each row.
 * @param title The text to be displayed over the Row
 * @param subjects The list of connections. (Visually Similar, Found In, etc).
 * @param imageLoader Necessary to display images for Radicals that do not have text
 * @param navigateToDetailScreen Navigates to the detail screen of the clicked Subject.
 */
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun NestedSubjectList(
    numberOfColumns: Int,
    title: String,
    subjects: List<Subject>,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.spacing.extraSmall,
                end = MaterialTheme.spacing.extraSmall,
                bottom = MaterialTheme.spacing.small,
            )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.spacing.small,
                ),
            text = title,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
        )
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        subjects.windowed(
            size = numberOfColumns,
            step = numberOfColumns,
            partialWindows = true,
        ).forEach { window ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                window.forEach { subject ->
                    Column(
                        modifier = Modifier
                            .width(screenWidth / numberOfColumns)
                    ) {
                        SubjectListItem(
                            subject = subject,
                            imageLoader = imageLoader,
                            onSelectSubject = navigateToDetailScreen
                        )
                    }
                }
            }
        }
    }
}