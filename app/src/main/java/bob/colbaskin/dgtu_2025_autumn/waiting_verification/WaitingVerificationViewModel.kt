package bob.colbaskin.dgtu_2025_autumn.waiting_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.AuthConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingVerificationViewModel @Inject constructor(
    private val userPreferences: UserPreferencesRepository
): ViewModel() {

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearUser()
            userPreferences.saveAuthStatus(AuthConfig.NOT_AUTHENTICATED)
        }
    }
}