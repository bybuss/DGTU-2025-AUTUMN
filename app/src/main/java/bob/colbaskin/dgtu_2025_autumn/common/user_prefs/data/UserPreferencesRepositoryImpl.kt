package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data

import android.util.Log
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.datastore.UserDataStore
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.UserPreferences
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.UserPreferencesRepository
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private const val TAG = "UserPreferences"

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: UserDataStore
): UserPreferencesRepository {

    override fun getUserPreferences(): Flow<UserPreferences> = dataStore.getUserPreferences()

    override suspend fun saveAuthStatus(status: AuthConfig) = dataStore.saveAuthStatus(status)

    override suspend fun saveOnboardingStatus(status: OnboardingConfig)
            = dataStore.saveOnboardingStatus(status)

    override suspend fun saveUserRole(role: Role) = dataStore.saveUserRole(role = role)

    override suspend fun saveUserInfo(
        userId: Int,
        userName: String,
        userEmail: String,
        role: Role
    ) = dataStore.saveUserInfo(
        userId = userId,
        userName = userName,
        userEmail = userEmail,
        role = role
    )

    override suspend fun saveBasicUserInfo(
        email: String,
        role: Role
    ) = dataStore.saveBasicUserInfo(
        email = email,
        role = role
    )

    override suspend fun clearUser() = dataStore.clearUser()
}
