package bob.colbaskin.dgtu_2025_autumn.onboarding.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStore: UserPreferencesRepository
): ViewModel() {

    fun action(action: OnBoardingAction) {
        when (action) {
            OnBoardingAction.OnboardingInProgress -> {
                viewModelScope.launch {
                    Log.d("LOG", "OnboardingInProgress")
                    dataStore.saveOnboardingStatus(OnboardingConfig.IN_PROGRESS)
                }
            }
            OnBoardingAction.OnboardingComplete -> {
                viewModelScope.launch {
                    Log.d("LOG", "OnboardingCompleted")
                    dataStore.saveOnboardingStatus(OnboardingConfig.COMPLETED)
                }
            }
        }
    }
}
