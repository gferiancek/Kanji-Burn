package com.gavinferiancek.kanjiburn.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screens(
    val route: String,
    val arguments: List<NamedNavArgument>,
) {

    object Login: Screens(
        route = "login",
        arguments = emptyList(),
    )

    object Dashboard: Screens(
        route = "dashboard",
        arguments = emptyList(),
    )

    object ReviewList: Screens(
        route = "reviewList",
        arguments = emptyList(),
    )

    object ReviewDetail: Screens(
        route = "reviewDetail",
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
