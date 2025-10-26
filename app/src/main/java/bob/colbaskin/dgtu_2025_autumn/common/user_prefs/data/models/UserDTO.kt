package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: Int,
    val email: String,
    val name: String,
    val role: String
)
