package com.gavinferiancek.kanjiburn.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screens(
    val route: String,
    val arguments: List<NamedNavArgument>,
) {
    object Dashboard: Screens(
        route = "dashboard",
        arguments = emptyList(),
    )

    object SubjectList: Screens(
        route = "subjectList",
        arguments = emptyList(),
    )

    object SubjectDetail: Screens(
        route = "subjectDetail",
        arguments = listOf(
            navArgument("subjectId") {
                type = NavType.IntType
            },
        ),
    )

    object Settings: Screens(
        route = "settings",
        arguments = emptyList(),
    )
}
