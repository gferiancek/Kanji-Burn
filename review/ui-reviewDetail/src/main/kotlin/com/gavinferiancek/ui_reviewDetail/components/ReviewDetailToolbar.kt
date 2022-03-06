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
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_domain.model.ReviewSubject
import com.gavinferiancek.theme.spacing
import com.gavinferiancek.ui_reviewDetail.R

@Composable
fun SubjectDetailToolbar(
    reviewSubject: ReviewSubject,
    srsStageName: String,
    srsResourceId: Int,
    color: Color,
    onNavigateUp: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(
                                top = MaterialTheme.spacing.extraSmall,
                                bottom = MaterialTheme.spacing.extraSmall,
                                start = MaterialTheme.spacing.small,
                                end = MaterialTheme.spacing.small,
                            ),
                        text = "Level ${reviewSubject.subject.level}",
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.h4,
                    )
                }
                Row {
                    Text(
                        modifier = Modifier
                            .padding(end = MaterialTheme.spacing.small)
                            .align(Alignment.CenterVertically),
                        text = srsStageName,
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.h4,
                    )
                    if (srsResourceId != 0) {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(0.75f),
                            painter = painterResource(id = srsResourceId),
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