package bob.colbaskin.dgtu_2025_autumn.admin_panel.presentation

import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.BackupItem
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.BackupSortType
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role

sealed interface AdminPanelAction {
    data class TabSelected(val newTabIndex: Int) : AdminPanelAction

    data class PageChanged(val page: Int) : AdminPanelAction

    data class UpdateName(val newName: String) : AdminPanelAction

    data class UpdatePassword(val newPassword: String): AdminPanelAction

    data class UpdateEmail(val newEmail: String): AdminPanelAction

    data class UpdateRole(val newRole: Role): AdminPanelAction


    data class UpdateDirectorySearchQuery(val query: String) : AdminPanelAction

    object AddNewDirectory : AdminPanelAction

    data class ToggleDirectoryExpanded(val directoryId: String) : AdminPanelAction

    data class StartAddingNewItem(val directoryId: String) : AdminPanelAction

    data class UpdateNewItemText(val text: String) : AdminPanelAction

    data class ConfirmNewItem(val directoryId: String) : AdminPanelAction

    data class CancelNewItem(val directoryId: String) : AdminPanelAction

    data class DeleteDirectory(val directoryId: String) : AdminPanelAction


    data class SelectBackupItem(val item: BackupItem) : AdminPanelAction

    data class UpdateBackupSort(val sortType: BackupSortType) : AdminPanelAction

    object CollapseBackupDetails : AdminPanelAction


    object GetAllUsers: AdminPanelAction

    object GetAllUnVerifyUsers: AdminPanelAction

    data class DeleteUserById(val userId: Int): AdminPanelAction

    data class VerifyUser(val userId: Int, val role: Role): AdminPanelAction
}
