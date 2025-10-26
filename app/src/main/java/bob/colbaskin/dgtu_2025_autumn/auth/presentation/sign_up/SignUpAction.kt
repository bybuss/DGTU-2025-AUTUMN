package bob.colbaskin.dgtu_2025_autumn.auth.presentation.sign_up

interface SignUpAction {
    data object SignIn : SignUpAction
    data object SignUp : SignUpAction
    data class UpdateUsername(val username: String) : SignUpAction
    data class UpdateEmail(val email: String): SignUpAction
    data class UpdatePassword(val password: String): SignUpAction
    data class UpdateConfirmPassword(val confirmPassword: String) : SignUpAction
}