package bob.colbaskin.dgtu_2025_autumn.admin_panel.presentation

import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.BackupItem
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.BackupSortType
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.Directory
import bob.colbaskin.dgtu_2025_autumn.common.UiState
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User

data class AdminPanelState(
    val allUserState: UiState<List<User>> = UiState.Loading,
    val unVerifyUserState: UiState<List<User>> = UiState.Loading,

    val deleteUserState: UiState<Unit> = UiState.Loading,
    val verifyUserState: UiState<Unit> = UiState.Loading,

    val selectedTab: Int = 0,
    val adminPanelState: UiState<Unit> = UiState.Loading,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val role: Role = Role.NOT_VERIFY,

    val directorySearchQuery: String = "",
    val directories: List<Directory> = emptyList(),
    val expandedDirectories: Set<String> = emptySet(),
    val addingNewItemDirectoryId: String? = null,
    val newItemText: String = "",

    val backupItems: List<BackupItem> = emptyList(),
    val selectedBackupItem: BackupItem? = null,
    val backupSortType: BackupSortType = BackupSortType.CHANGE_HISTORY,
    val isBackupDetailsExpanded: Boolean = false
) {
    val filteredDirectories: List<Directory>
        get() = if (directorySearchQuery.isBlank()) {
            directories
        } else {
            directories.filter {
                it.name.contains(directorySearchQuery, ignoreCase = true)
            }
        }
}
