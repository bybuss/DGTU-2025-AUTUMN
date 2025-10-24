package bob.colbaskin.dgtu_2025_autumn.auth.domain.token

import retrofit2.Response
import retrofit2.http.GET

interface RefreshTokenService {

    @GET("/refresh")
    suspend fun refresh(): Response<Unit>
}
