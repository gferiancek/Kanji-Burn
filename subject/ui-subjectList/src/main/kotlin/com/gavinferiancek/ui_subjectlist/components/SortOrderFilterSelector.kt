package com.gavinferiancek.ui_subjectlist.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme

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
                    .padding(end = 4.dp)
                    .align(Alignment.CenterVertically),
                checked = isSortOrderSelected,
                onCheckedChange = { filterBySortOrder() },
                colors = CheckboxDefaults.colors(KanjiBurnTheme.colors.primary),
            )
            Text(
                text = sortOrderText,
                fontSize = 22.sp
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
                    .padding(start = 24.dp),
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
                            .padding(end = 4.dp)
                            .align(Alignment.CenterVertically),
                        checked = isAscSelected,
                        onCheckedChange = { orderAsc() },
                        colors = CheckboxDefaults.colors(KanjiBurnTheme.colors.primary),
                    )
                    Text(
                        text = ascText,
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
                            .padding(end = 4.dp)
                            .align(Alignment.CenterVertically),
                        checked = isDescSelected,
                        onCheckedChange = { orderDesc() },
                        colors = CheckboxDefaults.colors(KanjiBurnTheme.colors.primary),
                    )
                    Text(
                        text = descText,
                    )
                }
            }
        }
    }
}