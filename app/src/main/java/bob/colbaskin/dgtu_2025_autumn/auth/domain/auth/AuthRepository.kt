package bob.colbaskin.dgtu_2025_autumn.auth.domain.auth

import bob.colbaskin.dgtu_2025_autumn.common.ApiResult

interface AuthRepository {

    suspend fun login(email: String, password: String): ApiResult<Unit>

    suspend fun register(email: String, password: String): ApiResult<Unit>
}
