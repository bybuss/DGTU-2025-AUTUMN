package bob.colbaskin.dgtu_2025_autumn.auth.data.models

import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User

fun UserProfileDTO.toDomain(): User {
    return User(
        id = this.id,
        email = this.email,
        name = this.name,
        role = when (this.role.lowercase()) {
            "admin" -> Role.ADMIN
            "analyst" -> Role.ANALYST
            "user" -> Role.USER
            else -> Role.NOT_VERIFY
        }
    )
}