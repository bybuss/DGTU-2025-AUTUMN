package bob.colbaskin.dgtu_2025_autumn.waiting_verification

sealed interface WaitingVerificationAction {
    data object Logout: WaitingVerificationAction
    data object RefreshVerificationStatus: WaitingVerificationAction
}