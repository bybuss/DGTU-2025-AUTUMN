package bob.colbaskin.dgtu_2025_autumn.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDTO(
    val id: Int,
    val email: String,
    val name: String,
    val role: String
)
