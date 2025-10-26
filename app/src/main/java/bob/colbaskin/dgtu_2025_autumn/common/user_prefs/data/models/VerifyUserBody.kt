package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models

import kotlinx.serialization.Serializable

@Serializable
data class VerifyUserBody(
    val userId: Int,
    val role: String
)
