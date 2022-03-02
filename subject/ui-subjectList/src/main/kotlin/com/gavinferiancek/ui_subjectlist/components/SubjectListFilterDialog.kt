package com.gavinferiancek.ui_subjectlist.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gavinferiancek.core.domain.FilterOrder
import com.gavinferiancek.subject_domain.SubjectFilter
import com.gavinferiancek.theme.accept
import com.gavinferiancek.theme.spacing

@Composable
fun SubjectListFilterDialog(
    subjectFilter: SubjectFilter,
    onUpdateSubjectFilter: (SubjectFilter) -> Unit,
    onCloseDialog: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onCloseDialog() },
        title = {
                Text(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.large),
                    text = "Filter Options",
                    style = MaterialTheme.typography.h2,
                )
        },
        text = {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Sort By",
                            style = MaterialTheme.typography.h3,
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        SortOrderFilterSelector(
                            sortOrderText = SubjectFilter.Level().uiValue,
                            isSortOrderSelected = subjectFilter is SubjectFilter.Level,
                            ascText = "1 -> 60",
                            isAscSelected = subjectFilter.order is FilterOrder.Ascending,
                            descText = "60 -> 1",
                            isDescSelected = subjectFilter.order is FilterOrder.Descending,
                            filterBySortOrder = { onUpdateSubjectFilter(SubjectFilter.Level()) },
                            orderAsc = {
                                onUpdateSubjectFilter(
                                    SubjectFilter.Level(
                                        order = FilterOrder.Ascending
                                    )
                                )
                            },
                            orderDesc = {
                                onUpdateSubjectFilter(
                                    SubjectFilter.Level(
                                        order = FilterOrder.Descending
                                    )
                                )
                            },
                        )
                        SortOrderFilterSelector(
                            sortOrderText = SubjectFilter.Reading().uiValue,
                            isSortOrderSelected = subjectFilter is SubjectFilter.Reading,
                            ascText = "あ -> ん",
                            isAscSelected = subjectFilter.order is FilterOrder.Ascending,
                            descText = "ん -> あ",
                            isDescSelected = subjectFilter.order is FilterOrder.Descending,
                            filterBySortOrder = { onUpdateSubjectFilter(SubjectFilter.Reading()) },
                            orderAsc = {
                                onUpdateSubjectFilter(
                                    SubjectFilter.Reading(
                                        order = FilterOrder.Ascending
                                    )
                                )
                            },
                            orderDesc = {
                                onUpdateSubjectFilter(
                                    SubjectFilter.Reading(
                                        order = FilterOrder.Descending
                                    )
                                )
                            },
                        )
                        SortOrderFilterSelector(
                            sortOrderText = SubjectFilter.Meaning().uiValue,
                            isSortOrderSelected = subjectFilter is SubjectFilter.Meaning,
                            ascText = "A -> Z",
                            isAscSelected = subjectFilter.order is FilterOrder.Ascending,
                            descText = "Z -> A",
                            isDescSelected = subjectFilter.order is FilterOrder.Descending,
                            filterBySortOrder = { onUpdateSubjectFilter(SubjectFilter.Meaning()) },
                            orderAsc = {
                                onUpdateSubjectFilter(
                                    SubjectFilter.Meaning(
                                        order = FilterOrder.Ascending
                                    )
                                )
                            },
                            orderDesc = {
                                onUpdateSubjectFilter(
                                    SubjectFilter.Meaning(
                                        order = FilterOrder.Descending
                                    )
                                )
                            },
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        Text(
                            text = "Included Levels",
                            style = MaterialTheme.typography.h3,
                        )
                    }
                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(
                    onClick = { onCloseDialog() },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = "Done",
                        tint = MaterialTheme.colors.accept,
                    )
                }
            }
        },
    )
}