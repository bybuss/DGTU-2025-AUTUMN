package bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.remote

import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.UserDTO
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.VerifyUserBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserService {

    @GET("/users/allVerify")
    suspend fun getAllUnVerifyUsers(): List<UserDTO>

    @GET("/users/all")
    suspend fun getAllUsers(): List<UserDTO>

    @DELETE("/users/delete")
    suspend fun deleteUserById(userId: Int): Response<Unit>

    @PATCH("/users/verify")
    suspend fun verifyUserById(@Body body: VerifyUserBody): Response<Unit>
}
