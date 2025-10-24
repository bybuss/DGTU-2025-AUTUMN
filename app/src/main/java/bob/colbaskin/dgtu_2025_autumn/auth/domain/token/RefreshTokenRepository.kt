package bob.colbaskin.dgtu_2025_autumn.auth.domain.token

import bob.colbaskin.dgtu_2025_autumn.common.ApiResult

interface RefreshTokenRepository {

    suspend fun refresh(): ApiResult<Unit>
}
