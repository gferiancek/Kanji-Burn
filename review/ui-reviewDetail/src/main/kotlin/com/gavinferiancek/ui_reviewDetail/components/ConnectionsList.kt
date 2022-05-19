package com.gavinferiancek.ui_reviewDetail.components

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
import com.gavinferiancek.core_ui.components.subject.SubjectListItem
import com.gavinferiancek.core_ui.theme.spacing

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun ConnectionsList(
    numberOfColumns: Int,
    title: String,
    connections: List<Subject>,
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
        /**
         * ConnectionsList is used inside of a LazyColumn, so we can't use a LazyVerticalGrid here.
         * Instead, we have to build our Grid row by row with the help of collections.windowed().
         */
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        connections.windowed(
            size = numberOfColumns,
            step = numberOfColumns,
            partialWindows = true,
        ).forEach { subjects ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                subjects.forEach { subject ->
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
