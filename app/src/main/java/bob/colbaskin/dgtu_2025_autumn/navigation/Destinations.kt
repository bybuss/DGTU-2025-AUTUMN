package bob.colbaskin.dgtu_2025_autumn.navigation

import bob.colbaskin.dgtu_2025_autumn.R
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role

enum class Destinations(
    val icon: Int,
    val label: String,
    val screen: Screens,
    val allowedRoles: List<Role> = listOf(Role.USER, Role.ANALYST, Role.ADMIN)
) {
    DASHBOARD(
        icon = R.drawable.layout_dashboard_2,
        label = "Дашборд",
        screen = Screens.Dashboard,
        allowedRoles = listOf(Role.USER, Role.ANALYST, Role.ADMIN)
    ),
    PROJECTS(
        icon = R.drawable.file_analytics_1,
        label = "Проекты",
        screen = Screens.Projects,
        allowedRoles = listOf(Role.USER, Role.ANALYST, Role.ADMIN)
    ),
    RAPPORTS(
        icon = R.drawable.chart_dots_2_1,
        label = "Отчеты",
        screen = Screens.Rapports,
        allowedRoles = listOf(Role.USER, Role.ANALYST, Role.ADMIN)
    ),
    ADMIN(
        icon = R.drawable.brand_codesandbox_1,
        label = "Админ",
        screen = Screens.Admin,
        allowedRoles = listOf(Role.ADMIN)
    )
}
