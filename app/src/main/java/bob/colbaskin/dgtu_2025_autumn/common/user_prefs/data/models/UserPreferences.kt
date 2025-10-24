package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models

import bob.colbaskin.datastore.OnboardingStatus
import bob.colbaskin.dgtu_2025_autumn.datastore.UserPreferencesProto
import bob.colbaskin.hack.datastore.AuthStatus

data class UserPreferences(
    val onboardingStatus: OnboardingConfig,
    val authStatus: AuthConfig
)

fun UserPreferencesProto.toData(): UserPreferences {
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
        }
    )
}
