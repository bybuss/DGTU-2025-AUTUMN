package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import bob.colbaskin.datastore.OnboardingStatus
import bob.colbaskin.datastore.UserRole
import bob.colbaskin.hack.datastore.AuthStatus
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.UserPreferences
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.toData
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User
import bob.colbaskin.dgtu_2025_autumn.datastore.UserPreferencesProto
import bob.colbaskin.dgtu_2025_autumn.datastore.copy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_FILE_NAME = "user_preferences.pb"
private const val TAG = "UserPreferences"

private val Context.userPreferencesStore: DataStore<UserPreferencesProto> by dataStore(
    fileName = USER_PREFERENCES_FILE_NAME,
    serializer = UserPreferencesSerializer
)

class UserDataStore(context: Context) {
    private val dataStore: DataStore<UserPreferencesProto> = context.userPreferencesStore

    fun getUserPreferences(): Flow<UserPreferences> = dataStore.data
        .map { proto ->
            Log.d(TAG, "Reading from DataStore: $proto")
            val result = proto.toData()
            Log.d(TAG, "Converted to: $result")
            result
        }

    suspend fun saveAuthStatus(status: AuthConfig) {
        Log.d(TAG, "saveAuthStatus: $status")
        dataStore.updateData { prefs ->
            prefs.copy {
                authStatus = when (status) {
                    AuthConfig.NOT_AUTHENTICATED -> AuthStatus.NOT_AUTHENTICATED
                    AuthConfig.AUTHENTICATED -> AuthStatus.AUTHENTICATED
                }
            }
        }
    }
    suspend fun saveOnboardingStatus(status: OnboardingConfig) {
        Log.d(TAG, "saveOnboardingStatus: $status")
        dataStore.updateData { prefs ->
            prefs.copy {
                onboardingStatus  = when (status) {
                    OnboardingConfig.NOT_STARTED -> OnboardingStatus.NOT_STARTED
                    OnboardingConfig.IN_PROGRESS -> OnboardingStatus.IN_PROGRESS
                    OnboardingConfig.COMPLETED -> OnboardingStatus.COMPLETED
                }
            }
        }
    }

    suspend fun saveUserInfo(userId: Int, userName: String, userEmail: String, role: Role) {
        Log.d(TAG, "saveUserInfo: userId=$userId, userName=$userName, email=$userEmail, role=$role")
        dataStore.updateData { prefs ->
            prefs.copy {
                this.userId = userId
                this.userName = userName
                this.userEmail = userEmail
                this.userRole = when (role) {
                    Role.ADMIN -> UserRole.Admin
                    Role.ANALYST -> UserRole.Analyst
                    Role.USER -> UserRole.User
                    Role.NOT_VERIFY -> UserRole.NotVerify
                }
                this.authStatus = AuthStatus.AUTHENTICATED
            }
        }
    }

    suspend fun saveBasicUserInfo(email: String, role: Role) {
        Log.d(TAG, "saveBasicUserInfo: email=$email, role=$role")
        dataStore.updateData { prefs ->
            prefs.copy {
                this.userId = 0
                this.userName = email.split("@").first()
                this.userEmail = email
                this.userRole = when (role) {
                    Role.ADMIN -> UserRole.Admin
                    Role.ANALYST -> UserRole.Analyst
                    Role.USER -> UserRole.User
                    Role.NOT_VERIFY -> UserRole.NotVerify
                }
                this.authStatus = AuthStatus.AUTHENTICATED
            }
        }
    }

    suspend fun clearUser() {
        Log.d(TAG, "clearUser")
        dataStore.updateData { prefs ->
            prefs.copy {
                clearUserId()
                clearUserName()
                clearUserEmail()
                clearUserRole()
            }
        }
    }

    suspend fun saveUserRole(role: Role) {
        Log.d(TAG, "saveUserRole: $role")
        dataStore.updateData { prefs ->
            prefs.copy {
                userRole = when (role) {
                    Role.ADMIN -> UserRole.Admin
                    Role.ANALYST -> UserRole.Analyst
                    Role.USER -> UserRole.User
                    Role.NOT_VERIFY -> UserRole.NotVerify
                }
            }
        }
    }
}
