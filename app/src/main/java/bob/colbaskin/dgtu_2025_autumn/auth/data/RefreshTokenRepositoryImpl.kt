package bob.colbaskin.dgtu_2025_autumn.auth.data

import android.util.Log
import bob.colbaskin.dgtu_2025_autumn.auth.domain.token.RefreshTokenRepository
import bob.colbaskin.dgtu_2025_autumn.auth.domain.token.RefreshTokenService
import bob.colbaskin.dgtu_2025_autumn.common.ApiResult
import bob.colbaskin.dgtu_2025_autumn.common.utils.safeApiCall
import jakarta.inject.Inject
import retrofit2.Response


private const val TAG = "Auth"

class RefreshTokenRepositoryImpl @Inject constructor(
    private val tokenApi: RefreshTokenService
): RefreshTokenRepository {
    override suspend fun refresh(): ApiResult<Unit> {
        Log.d(TAG, "Attempting refresh token")
        return safeApiCall<Response<Unit>, Unit>(
            apiCall = {
                tokenApi.refresh()
            },
            successHandler = { response ->
                Log.d(TAG, "Refresh successful")
                response
            }
        )
    }
}
