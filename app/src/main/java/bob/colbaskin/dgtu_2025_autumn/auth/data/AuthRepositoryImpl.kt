package bob.colbaskin.dgtu_2025_autumn.auth.data

import android.util.Log
import bob.colbaskin.dgtu_2025_autumn.auth.data.models.LoginBody
import bob.colbaskin.dgtu_2025_autumn.auth.data.models.RegisterBody
import bob.colbaskin.dgtu_2025_autumn.auth.data.models.RegisterDTO
import bob.colbaskin.dgtu_2025_autumn.auth.data.models.UserProfileDTO
import bob.colbaskin.dgtu_2025_autumn.auth.data.models.toDomain
import bob.colbaskin.dgtu_2025_autumn.auth.domain.auth.AuthApiService
import bob.colbaskin.dgtu_2025_autumn.auth.domain.auth.AuthRepository
import bob.colbaskin.dgtu_2025_autumn.common.ApiResult
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.UserPreferencesRepository
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User
import bob.colbaskin.dgtu_2025_autumn.common.utils.safeApiCall
import jakarta.inject.Inject
import retrofit2.Response

private const val TAG = "Auth"

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApiService,
    private val userPreferences: UserPreferencesRepository
): AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): ApiResult<Unit> {
        Log.d(TAG, "Attempting login for user: $email")
        return safeApiCall<Response<Unit>, Unit>(
            apiCall = {
                authApi.login(
                    body = LoginBody(
                        email = email,
                        password = password
                    )
                )
            },
            successHandler = { response ->
                Log.d(TAG, "Login successful. Saving Authenticated status")
                userPreferences.saveAuthStatus(AuthConfig.AUTHENTICATED)
                userPreferences.saveBasicUserInfo(
                    email = email,
                    role = Role.NOT_VERIFY
                )
                response
            }
        )
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String
    ): ApiResult<Unit> {
        Log.d(TAG, "Attempting register for user: $email")
        return safeApiCall<RegisterDTO, Unit>(
            apiCall = {
                authApi.register(
                    body = RegisterBody(
                        email = email,
                        password = password,
                        name = name
                    )
                )
            },
            successHandler = { response ->
                Log.d(TAG, "Register successful. Saving Authenticated status")
                userPreferences.saveAuthStatus(AuthConfig.AUTHENTICATED)
                userPreferences.saveUserInfo(
                    userId = response.id,
                    userName = response.name,
                    userEmail = response.email,
                    role = Role.NOT_VERIFY
                )
                response
            }
        )
    }

    override suspend fun getCurrentUser(): ApiResult<User> {
        return safeApiCall<UserProfileDTO, User>(
            apiCall = { authApi.getCurrentUser() },
            successHandler = { response ->
                Log.d(TAG, "Current user data: $response")
                userPreferences.saveUserInfo(
                    userId = response.id,
                    userName = response.name,
                    userEmail = response.email,
                    role = response.toDomain().role
                )
                response.toDomain()
            }
        )
    }
}
