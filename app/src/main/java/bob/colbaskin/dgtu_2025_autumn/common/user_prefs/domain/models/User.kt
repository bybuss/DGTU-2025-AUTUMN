package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models

import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role

data class User(
    val id: Int,
    val email: String,
    val name: String = "",
    val role: Role = Role.NOT_VERIFY
)
