package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_ui.SrsStageData
import com.gavinferiancek.core_ui.theme.spacing

@ExperimentalCoilApi
@Composable
fun ReviewDetailToolbar(
    subject: Subject,
    srsStageData: SrsStageData,
    color: Color,
    onNavigateUp: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = MaterialTheme.spacing.small,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Level ${subject.level}",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary,
                )
                Row {
                    Text(
                        modifier = Modifier
                            .padding(end = MaterialTheme.spacing.small)
                            .align(Alignment.CenterVertically),
                        text = srsStageData.uiValue,
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.h4,
                    )
                    if (srsStageData.imageResource != -1) {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(0.75f),
                            painter = painterResource(id = srsStageData.imageResource),
                            contentDescription = "Icon representing the current SRS Stage",
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateUp
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back button to navigate to previous page",
                    tint = MaterialTheme.colors.onPrimary,
                )
            }
        },
        backgroundColor = color,
        elevation = MaterialTheme.spacing.none,
    )
}