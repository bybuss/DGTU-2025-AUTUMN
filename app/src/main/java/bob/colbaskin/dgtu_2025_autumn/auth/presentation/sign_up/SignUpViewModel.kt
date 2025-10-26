package bob.colbaskin.dgtu_2025_autumn.auth.presentation.sign_up

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.dgtu_2025_autumn.auth.domain.auth.AuthRepository
import bob.colbaskin.dgtu_2025_autumn.common.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

private const val TAG = "Auth"

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,

): ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    fun onAction(action: SignUpAction) {
        when (action) {
            SignUpAction.SignUp -> register()
            is SignUpAction.UpdateUsername -> updateUsername(action.username)
            is SignUpAction.UpdateEmail -> updateEmail(action.email)
            is SignUpAction.UpdatePassword -> updatePassword(action.password)
            is SignUpAction.UpdateConfirmPassword -> updateConfirmPassword(action.confirmPassword)
            else -> Unit
        }
    }

    private fun register() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val response = authRepository.register(
                email = state.email,
                password = state.password,
                name = state.name
            ).toUiState()

            Log.d(TAG, "Data from VM register: $response")

            state = state.copy(
                authState = response,
                isLoading = false
            )
        }
    }

    private fun updateUsername(username: String) {
        state = state.copy(name = username)
    }

    private fun updateEmail(email: String) {
        state = state.copy(email = email)
    }

    private fun updatePassword(password: String) {
        state = state.copy(password = password)
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        state = state.copy(confirmPassword = confirmPassword)
    }
}
