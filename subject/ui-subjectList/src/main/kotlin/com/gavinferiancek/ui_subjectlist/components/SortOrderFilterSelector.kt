package com.gavinferiancek.ui_subjectlist.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gavinferiancek.theme.spacing

@Composable
fun SortOrderFilterSelector(
    sortOrderText: String,
    isSortOrderSelected: Boolean,
    ascText: String,
    isAscSelected: Boolean,
    descText: String,
    isDescSelected: Boolean,
    filterBySortOrder: () -> Unit,
    orderAsc: () -> Unit,
    orderDesc: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    enabled = true,
                    onClick = { filterBySortOrder() }
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                modifier = Modifier
                    .padding(end = MaterialTheme.spacing.extraSmall)
                    .align(Alignment.CenterVertically),
                checked = isSortOrderSelected,
                onCheckedChange = { filterBySortOrder() },
                colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
            )
            Text(
                text = sortOrderText,
                style = MaterialTheme.typography.h3,
            )
        }
        AnimatedVisibility(
            visible = isSortOrderSelected,
            enter = slideInHorizontally(animationSpec = tween(400))
                    + expandVertically(animationSpec = tween(100))
                    + fadeIn(animationSpec = tween(400)),
            exit = slideOutHorizontally(animationSpec = tween(400))
                    + shrinkVertically(animationSpec = tween(100))
                    + fadeOut(animationSpec = tween(400)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.spacing.large),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            enabled = true,
                            onClick = { orderAsc() }
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        modifier = Modifier
                            .padding(end = MaterialTheme.spacing.extraSmall)
                            .align(Alignment.CenterVertically),
                        checked = isAscSelected,
                        onCheckedChange = { orderAsc() },
                        colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
                    )
                    Text(
                        text = ascText,
                        style = MaterialTheme.typography.body2
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            enabled = true,
                            onClick = { orderDesc() }
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        modifier = Modifier
                            .padding(end = MaterialTheme.spacing.extraSmall)
                            .align(Alignment.CenterVertically),
                        checked = isDescSelected,
                        onCheckedChange = { orderDesc() },
                        colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
                    )
                    Text(
                        text = descText,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}