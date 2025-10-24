package bob.colbaskin.dgtu_2025_autumn.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDTO(
    val id: Int,
    val email: String
)
