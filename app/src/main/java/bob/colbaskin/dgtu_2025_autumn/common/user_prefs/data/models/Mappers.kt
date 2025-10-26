package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models

import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User

fun UserDTO.toDomain(): User {
    return User(
        id = this.id,
        email = this.email,
        name = this.name,
        role = this.role.toRole()
    )
}

fun String.toRole(): Role {
    return when (this.lowercase()) {
        "admin" -> Role.ADMIN
        "analyst" -> Role.ANALYST
        "user" -> Role.USER
        else -> Role.NOT_VERIFY
    }
}

fun Role.toStringRole(): String {
    return when (this) {
        Role.ADMIN -> "Admin"
        Role.ANALYST -> "Analyst"
        Role.USER -> "User"
        Role.NOT_VERIFY -> "NotVerify"
    }
}
