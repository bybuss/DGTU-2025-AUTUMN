package bob.colbaskin.dgtu_2025_autumn.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterBody(
    val email: String,
    val password: String,
    val name: String
)
