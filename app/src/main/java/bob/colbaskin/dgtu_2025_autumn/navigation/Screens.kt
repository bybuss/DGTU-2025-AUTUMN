package bob.colbaskin.dgtu_2025_autumn.navigation

import kotlinx.serialization.Serializable

interface Screens {
    @Serializable
    data object Dashboard: Screens

    @Serializable
    data object Projects: Screens

    @Serializable
    data object Rapports: Screens

    @Serializable
    data object Admin: Screens

    @Serializable
    data object Welcome: Screens

    @Serializable
    data object Introduction: Screens

    @Serializable
    data object SignIn: Screens

    @Serializable
    data object SignUp: Screens

    @Serializable
    data object WaitingVerification: Screens
}
