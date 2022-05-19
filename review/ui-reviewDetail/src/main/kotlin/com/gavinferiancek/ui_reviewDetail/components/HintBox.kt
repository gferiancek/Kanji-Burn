package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.gavinferiancek.core_ui.theme.spacing
import com.gavinferiancek.ui_reviewDetail.R

@Composable
fun HintBox(
    hint: String,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small)
            .alpha(0.8f),
        color = MaterialTheme.colors.background,
        shape = MaterialTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .alpha(0.8f),
                    painter = painterResource(id = R.drawable.ic_baseline_help_18),
                    contentDescription = "Hint box icon",
                    tint = MaterialTheme.colors.onSurface,
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.spacing.extraSmall,
                        ),
                    text = "Hint",
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body2
                )
            }
            Text(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.small,
                        bottom = MaterialTheme.spacing.small,
                    ),
                text = hint,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
            )
        }
    }
}