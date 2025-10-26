package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models

import android.util.Log
import bob.colbaskin.datastore.OnboardingStatus
import bob.colbaskin.datastore.UserRole
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User
import bob.colbaskin.dgtu_2025_autumn.datastore.UserPreferencesProto
import bob.colbaskin.hack.datastore.AuthStatus

private const val TAG = "UserPreferences"

data class UserPreferences(
    val onboardingStatus: OnboardingConfig,
    val authStatus: AuthConfig,
    val userRole: Role,
    val user: User?
)

fun UserPreferencesProto.toData(): UserPreferences {
    Log.d(TAG, "toData() - userId: ${this.userId}, userRole: ${this.userRole}")

    val user = if (this.userId != 0 && this.userName.isNotEmpty()) {
        User(
            id = this.userId,
            email = this.userEmail,
            name = this.userName,
            role = when (this.userRole) {
                UserRole.Admin -> Role.ADMIN
                UserRole.Analyst -> Role.ANALYST
                UserRole.User -> Role.USER
                UserRole.NotVerify -> Role.NOT_VERIFY
                else -> Role.NOT_VERIFY
            }
        )
    } else {
        null
    }

    return UserPreferences(
        onboardingStatus = when (this.onboardingStatus) {
            OnboardingStatus.NOT_STARTED -> OnboardingConfig.NOT_STARTED
            OnboardingStatus.IN_PROGRESS -> OnboardingConfig.IN_PROGRESS
            OnboardingStatus.COMPLETED -> OnboardingConfig.COMPLETED
            OnboardingStatus.UNRECOGNIZED, null -> OnboardingConfig.NOT_STARTED
        },
        authStatus = when (this.authStatus) {
            AuthStatus.AUTHENTICATED -> AuthConfig.AUTHENTICATED
            AuthStatus.NOT_AUTHENTICATED -> AuthConfig.NOT_AUTHENTICATED
            AuthStatus.UNRECOGNIZED, null -> AuthConfig.NOT_AUTHENTICATED
        },
        userRole = when (this.userRole) {
            UserRole.Admin -> Role.ADMIN
            UserRole.Analyst -> Role.ANALYST
            UserRole.User -> Role.USER
            UserRole.NotVerify -> Role.NOT_VERIFY
            else -> Role.NOT_VERIFY
        },
        user = user
    )
}
