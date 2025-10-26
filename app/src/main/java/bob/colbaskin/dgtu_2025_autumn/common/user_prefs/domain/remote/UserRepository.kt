package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.remote

import bob.colbaskin.dgtu_2025_autumn.common.ApiResult
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User

interface UserRepository {
    suspend fun getAllUnVerifyUsers(): ApiResult<List<User>>

    suspend fun getAllUsers(): ApiResult<List<User>>

    suspend fun deleteUserById(userId: Int): ApiResult<Unit>

    suspend fun verifyUserById(userId: Int, role: Role): ApiResult<Unit>
}