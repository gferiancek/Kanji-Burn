package com.gavinferiancek.kanjiburn.ui.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.gavinferiancek.kanjiburn.R

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
            }
        ),
    )

    object Settings: Screens(
        route = "settings",
        arguments = emptyList(),
    )
}
