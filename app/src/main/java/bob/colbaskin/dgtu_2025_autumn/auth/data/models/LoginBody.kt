package bob.colbaskin.dgtu_2025_autumn.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val email: String,
    val password: String,
    val system: String = "Mobile"
)
