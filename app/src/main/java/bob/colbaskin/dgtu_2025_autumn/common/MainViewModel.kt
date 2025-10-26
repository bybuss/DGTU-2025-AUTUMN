package bob.colbaskin.dgtu_2025_autumn.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.dgtu_2025_autumn.auth.domain.auth.AuthRepository
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.UserPreferences
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserPreferencesRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _isCheckingVerification = MutableStateFlow(false)
    val isCheckingVerification: StateFlow<Boolean> = _isCheckingVerification

    val uiState: StateFlow<UiState<UserPreferences>> = repository.getUserPreferences()
        .map { preferences ->
            if (requiresVerification(preferences)) {
                checkVerificationStatusAutomatically()
            }
            UiState.Success(preferences)
        }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            initialValue = UiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    init {
        viewModelScope.launch {
            val currentPrefs = repository.getUserPreferences().first()
            if (currentPrefs.authStatus == AuthConfig.AUTHENTICATED) {
                checkVerificationStatusAutomatically()
            }
        }
    }

    private fun checkVerificationStatusAutomatically() {
        viewModelScope.launch {
            _isCheckingVerification.value = true
            when (val result = authRepository.getCurrentUser()) {
                is ApiResult.Success -> {
                    Log.d("Verification", "Verification check completed: ${result.data.role}")
                }
                is ApiResult.Error -> {
                    Log.e("Verification", "Verification check failed: ${result.text}")
                }
            }
            _isCheckingVerification.value = false
        }
    }

    fun requiresVerification(userPreferences: UserPreferences): Boolean {
        return userPreferences.authStatus == AuthConfig.AUTHENTICATED &&
                userPreferences.userRole == Role.NOT_VERIFY
    }

    fun refreshVerificationStatus() {
        viewModelScope.launch {
            val currentPrefs = repository.getUserPreferences().first()
            if (requiresVerification(currentPrefs)) {
                checkVerificationStatusAutomatically()
            }
        }
    }
}
