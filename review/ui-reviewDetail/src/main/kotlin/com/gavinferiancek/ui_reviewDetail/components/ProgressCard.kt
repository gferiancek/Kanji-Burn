package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.assignment.formatAvailableAt
import com.gavinferiancek.core_domain.assignment.formatUnlockedAt
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_ui.components.KanjiBurnCard


/**
 * Composable that shows user progress for a given subject.
 *
 * @param isRadical If the subject is a Radical, then it doesn't have a reading. That means we only
 * need to show the Meaning percentage. Defaults to false since 2/3 of the Subject types are not a Radical.
 * @param assignment Assignment object that contains info for the bottom half of the card. (Unlock date,
 * next review, etc.)
 * @param reviewStatistics ReviewStatistics object that contains info for correct vs incorrect answers.
 * @param color Color for the subject type that is used for theming.
 */
@Composable
fun ProgressCard(
    isRadical: Boolean = false,
    assignment: Assignment,
    reviewStatistics: ReviewStatistics,
    color: Color,
) {
    KanjiBurnCard {
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
                title = "Reading Answered Correct",
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
                    text = "Unlocked Date",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = assignment.formatUnlockedAt(),
                    style = MaterialTheme.typography.overline
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Next Review",
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    text = assignment.formatAvailableAt(),
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
                    text = assignment.formatUnlockedAt(),
                    style = MaterialTheme.typography.overline
                )
            }
        }
    }
}