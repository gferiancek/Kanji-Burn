package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gavinferiancek.core_ui.theme.spacing

@Composable
fun ProgressCardItem(
    displayStreakInfo: Boolean = false,
    percentageCorrect: Float,
    title: String,
    totalAttempts: Int,
    currentStreak: Int = 0,
    maxStreak: Int = 0,
    color: Color,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = MaterialTheme.spacing.small,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
            )
            /**
             * We want to display the percentage text right above the LinearProgressIndicator. If we are not
             * displaying streak info, then we want it in this row.  If we are, we display it in the next
             * row inside the if (displayStreakInfo) {} block of code.
             */
            if (!displayStreakInfo) {
                Text(
                    text = "${(percentageCorrect * 100).toInt()}%",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        if (displayStreakInfo) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    Text(
                        text = "Current Streak: $currentStreak",
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = MaterialTheme.spacing.small),
                        text = "Max Streak: $maxStreak",
                        style = MaterialTheme.typography.caption,
                    )
                }
                Text(
                    text = "${(percentageCorrect * 100).toInt()}%",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(),
            progress = percentageCorrect,
            color = color,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "0",
                style = MaterialTheme.typography.overline
            )
            Text(
                text = "$totalAttempts",
                style = MaterialTheme.typography.overline
            )
        }
    }
}