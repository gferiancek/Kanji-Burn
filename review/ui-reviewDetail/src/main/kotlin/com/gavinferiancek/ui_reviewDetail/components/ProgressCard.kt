package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_ui.components.TitledCardView
import com.gavinferiancek.core_ui.theme.spacing


@Composable
fun ProgressCard(
    title: String,
    isRadical: Boolean = false,
    assignment: Assignment,
    reviewStatistics: ReviewStatistics,
    color: Color,
) {
    TitledCardView(
        title = title,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            if (isRadical) {
                ProgressCardItem(
                    displayStreakInfo = true,
                    percentageCorrect = reviewStatistics.getTotalCorrectPercentage(),
                    title = "Name Answered Correct",
                    totalAttempts = reviewStatistics.getMeaningAttempts(),
                    currentStreak = reviewStatistics.meaningCurrentStreak,
                    maxStreak = reviewStatistics.meaningMaxStreak,
                    color = color,
                )
            } else {
                ProgressCardItem(
                    percentageCorrect = reviewStatistics.getTotalCorrectPercentage(),
                    title = "Combined Answered Correct",
                    totalAttempts = reviewStatistics.getTotalAttempts(),
                    color = color,
                )
                ProgressCardItem(
                    displayStreakInfo = true,
                    percentageCorrect = reviewStatistics.getMeaningCorrectPercentage(),
                    title = "Meaning Answered Correct",
                    totalAttempts = reviewStatistics.getMeaningAttempts(),
                    currentStreak = reviewStatistics.meaningCurrentStreak,
                    maxStreak = reviewStatistics.meaningMaxStreak,
                    color = color,
                )
                ProgressCardItem(
                    displayStreakInfo = true,
                    percentageCorrect = reviewStatistics.getReadingCorrectPercentage(),
                    title = "Meaning Answered Correct",
                    totalAttempts = reviewStatistics.getReadingAttempts(),
                    currentStreak = reviewStatistics.readingCurrentStreak,
                    maxStreak = reviewStatistics.readingMaxStreak,
                    color = color,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Next Review",
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = "Available Now",
                        style = MaterialTheme.typography.overline
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Unlocked Date",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "Yesterday",
                        style = MaterialTheme.typography.overline
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Burn Date",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "Tomorrow",
                        style = MaterialTheme.typography.overline
                    )
                }
            }
        }
    }
}