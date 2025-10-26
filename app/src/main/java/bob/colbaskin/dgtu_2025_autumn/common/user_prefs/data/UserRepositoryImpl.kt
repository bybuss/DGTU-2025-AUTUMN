package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data

import bob.colbaskin.dgtu_2025_autumn.common.ApiResult
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.VerifyUserBody
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.toDomain
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.toStringRole
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.remote.UserRepository
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.remote.UserService
import bob.colbaskin.dgtu_2025_autumn.common.utils.safeApiCall
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserService
): UserRepository {

    override suspend fun getAllUnVerifyUsers(): ApiResult<List<User>> {
        return safeApiCall(
            apiCall = { userApi.getAllUnVerifyUsers() },
            successHandler = { response ->
                response.map { it.toDomain() }
            }
        )
    }

    override suspend fun getAllUsers(): ApiResult<List<User>> {
        return safeApiCall(
            apiCall = { userApi.getAllUsers() },
            successHandler = { response ->
                response.map { it.toDomain() }
            }
        )
    }

    override suspend fun deleteUserById(userId: Int): ApiResult<Unit> {
        return safeApiCall<Response<Unit>, Unit>(
            apiCall = { userApi.deleteUserById(userId) },
            successHandler = { response ->
                response
            }
        )
    }

    override suspend fun verifyUserById(
        userId: Int,
        role: Role
    ): ApiResult<Unit> {
        return safeApiCall<Response<Unit>, Unit>(
            apiCall = { 
                userApi.verifyUserById(
                    body = VerifyUserBody(
                        userId = userId,
                        role = role.toStringRole()
                    )
                )
            },
            successHandler = { response ->
                response
            }
        )
    }
}