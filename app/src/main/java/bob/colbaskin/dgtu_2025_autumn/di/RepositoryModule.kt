package bob.colbaskin.dgtu_2025_autumn.di

import bob.colbaskin.dgtu_2025_autumn.auth.data.AuthRepositoryImpl
import bob.colbaskin.dgtu_2025_autumn.auth.data.RefreshTokenRepositoryImpl
import bob.colbaskin.dgtu_2025_autumn.auth.domain.auth.AuthApiService
import bob.colbaskin.dgtu_2025_autumn.auth.domain.auth.AuthRepository
import bob.colbaskin.dgtu_2025_autumn.auth.domain.token.RefreshTokenRepository
import bob.colbaskin.dgtu_2025_autumn.auth.domain.token.RefreshTokenService
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.UserPreferencesRepositoryImpl
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.UserRepositoryImpl
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.datastore.UserDataStore
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.UserPreferencesRepository
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.remote.UserRepository
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(dataStore: UserDataStore): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApiService,
        userPreferences: UserPreferencesRepository
    ): AuthRepository {
        return AuthRepositoryImpl(
            authApi = authApi,
            userPreferences = userPreferences
        )
    }

    @Provides
    @Singleton
    fun provideRefreshTokenService(retrofit: Retrofit): RefreshTokenService {
        return retrofit.create(RefreshTokenService::class.java)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenRepository(
        tokenApi: RefreshTokenService
    ): RefreshTokenRepository {
        return RefreshTokenRepositoryImpl(
            tokenApi = tokenApi
        )
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserService
    ): UserRepository {
        return UserRepositoryImpl(
            userApi = userApi
        )
    }
}
