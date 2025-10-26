package bob.colbaskin.dgtu_2025_autumn.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role

@Composable
fun BottomNavBar(
    navController: NavHostController,
    userRole: Role?
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val visibleDestinations = Destinations.entries.filter { destination ->
        userRole in destination.allowedRoles
    }

    NavigationBar (
        containerColor = Color.White
    ) {
        visibleDestinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any {
                it.hasRoute(destination.screen::class)
            } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.label
                    )
                },
                label = { Text(text = destination.label) },
                selected = selected,
                onClick = {
                    navController.navigate(destination.screen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color(0xFF717171),
                    unselectedTextColor = Color(0xFF717171)
                )
            )
        }
    }
}
