package bob.colbaskin.dgtu_2025_autumn.auth.presentation.sign_up

import android.util.Patterns
import bob.colbaskin.dgtu_2025_autumn.common.UiState

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val authState: UiState<Unit> = UiState.Loading
) {
    val isEmailValid: Boolean
        get() = email.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordEquals: Boolean
        get() = password == confirmPassword

    val isFormValid: Boolean
        get() = name.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                !isLoading &&
                isEmailValid &&
                isPasswordEquals
}
