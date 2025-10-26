package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain

import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.UserPreferences
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getUserPreferences(): Flow<UserPreferences>

    suspend fun saveAuthStatus(status: AuthConfig)

    suspend fun saveOnboardingStatus(status: OnboardingConfig)

    suspend fun saveUserRole(role: Role)

    suspend fun saveUserInfo(userId: Int, userName: String, userEmail: String, role: Role)

    suspend fun saveBasicUserInfo(email: String, role: Role)

    suspend fun clearUser()
}
