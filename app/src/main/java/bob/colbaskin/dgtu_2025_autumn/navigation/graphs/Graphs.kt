package bob.colbaskin.dgtu_2025_autumn.navigation.graphs

import kotlinx.serialization.Serializable

interface Graphs {

    @Serializable
    data object Main: Graphs

    @Serializable
    data object Onboarding: Graphs

    @Serializable
    data object Detailed: Graphs

    @Serializable
    object Verification: Graphs
}
