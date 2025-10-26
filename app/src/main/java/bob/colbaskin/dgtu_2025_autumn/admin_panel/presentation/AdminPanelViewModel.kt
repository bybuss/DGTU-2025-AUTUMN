package bob.colbaskin.dgtu_2025_autumn.admin_panel.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.Author
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.BackupItem
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.BackupSortType
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.ChangeDescription
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.Directory
import bob.colbaskin.dgtu_2025_autumn.common.UiState
import bob.colbaskin.dgtu_2025_autumn.common.toUiState
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.remote.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminPanelViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var state by mutableStateOf(AdminPanelState())
        private set

    init {
        state = state.copy(
            directories = listOf(
                Directory(
                    id = "1",
                    name = "Услуги",
                    items = listOf("Интернет", "Телефония", "Инфобез")
                ),
                Directory(
                    id = "2",
                    name = "Цифровые сервисы",
                    items = listOf("Облачные сервисы", "Отраслевые решения")
                ),
                Directory(
                    id = "3",
                    name = "Статьи затрат",
                    items = listOf("Семент", "Индикатор", "Вид затрат")
                )
            )
        )
        state = state.copy(
            backupItems = listOf(
                BackupItem(
                    id = 1,
                    projectName = "Структура реализации",
                    changeDate = "07.04.2025",
                    author = Author(
                        name = "Зубенко Михаил Петрович",
                        role = "Аналитик",
                        position = "Администратор"
                    ),
                    changeDescription = ChangeDescription(
                        title = "Изменения этапа проекта",
                        before = "Проектирование",
                        after = "Реализация"
                    )
                ),
                BackupItem(
                    id = 2,
                    projectName = "Структура реализации",
                    changeDate = "07.04.2025",
                    author = Author(
                        name = "Иванов Иван Иванович",
                        role = "Разработчик",
                        position = "Team Lead"
                    ),
                    changeDescription = ChangeDescription(
                        title = "Изменения в архитектуре",
                        before = "Монолит",
                        after = "Микросервисы"
                    )
                ),
            )
        )
    }

    fun onAction(action: AdminPanelAction) {
        when (action) {
            is AdminPanelAction.TabSelected -> updateSelectedTab(action.newTabIndex)
            is AdminPanelAction.PageChanged -> pageChanged(action.page)
            is AdminPanelAction.UpdateName -> updateName(action.newName)
            is AdminPanelAction.UpdateRole -> updateRole(action.newRole)
            is AdminPanelAction.UpdateEmail -> updateEmail(action.newEmail)
            is AdminPanelAction.UpdatePassword -> updatePassword(action.newPassword)

            is AdminPanelAction.UpdateDirectorySearchQuery -> updateDirectorySearchQuery(action.query)

            is AdminPanelAction.AddNewDirectory -> addNewDirectory()
            is AdminPanelAction.ToggleDirectoryExpanded -> toggleDirectoryExpanded(action.directoryId)

            is AdminPanelAction.StartAddingNewItem -> startAddingNewItem(action.directoryId)
            is AdminPanelAction.UpdateNewItemText -> updateNewItemText(action.text)
            is AdminPanelAction.ConfirmNewItem -> confirmNewItem(action.directoryId)
            is AdminPanelAction.CancelNewItem -> cancelNewItem(action.directoryId)
            is AdminPanelAction.DeleteDirectory -> deleteDirectory(action.directoryId)

            is AdminPanelAction.SelectBackupItem -> selectBackupItem(action.item)
            is AdminPanelAction.UpdateBackupSort -> updateBackupSort(action.sortType)
            is AdminPanelAction.CollapseBackupDetails -> collapseBackupDetails()

            is AdminPanelAction.GetAllUnVerifyUsers -> getAllUnVerifyUsers()
            is AdminPanelAction.GetAllUsers -> getAllUsers()

            is AdminPanelAction.DeleteUserById -> deleteUserById(action.userId)
            is AdminPanelAction.VerifyUser -> verifyUser(action.userId, action.role)
        }
    }

    private fun deleteUserById(userId: Int) {
        state = state.copy(deleteUserState = UiState.Loading)
        viewModelScope.launch {
            val response = userRepository.deleteUserById(userId).toUiState()
            state = when (response) {
                is UiState.Success -> {
                    val deleteUserStateData = response.data
                    state.copy(deleteUserState = UiState.Success(deleteUserStateData))
                }

                else -> state.copy(deleteUserState = response)
            }
        }
    }

    private fun verifyUser(userId: Int, role: Role) {
        state = state.copy(verifyUserState = UiState.Loading)
        viewModelScope.launch {
            val response = userRepository.verifyUserById(userId, role).toUiState()
            state = when (response) {
                is UiState.Success -> {
                    val verifyUserStateData = response.data
                    state.copy(verifyUserState = UiState.Success(verifyUserStateData))
                }

                else -> state.copy(verifyUserState = response)
            }
        }
    }

    private fun getAllUnVerifyUsers() {
        state = state.copy(unVerifyUserState = UiState.Loading)
        viewModelScope.launch {
            val response = userRepository.getAllUnVerifyUsers().toUiState()
            state = when (response) {
                is UiState.Success -> {
                    val unVerifyUserStateData = response.data
                    state.copy(unVerifyUserState = UiState.Success(unVerifyUserStateData))
                }
                else -> state.copy(unVerifyUserState = response)
            }
        }
    }

    private fun getAllUsers() {
        state = state.copy(allUserState = UiState.Loading)
        viewModelScope.launch {
            val response = userRepository.getAllUnVerifyUsers().toUiState()
            state = when (response) {
                is UiState.Success -> {
                    val unVerifyUserStateData = response.data
                    state.copy(allUserState = UiState.Success(unVerifyUserStateData))
                }
                else -> state.copy(unVerifyUserState = response)
            }
        }
    }

    private fun updateName(newName: String) {
        state = state.copy(name = newName)
    }

    private fun updateRole(newRole: Role) {
        state = state.copy(role = newRole)
    }

    private fun updateEmail(newEmail: String) {
        state = state.copy(email = newEmail)
    }

    private fun updatePassword(newPassword: String) {
        state = state.copy(password = newPassword)
    }

    private fun updateSelectedTab(newTabIndex: Int) {
        state = state.copy(selectedTab = newTabIndex)
    }

    private fun pageChanged(page: Int) {
        if (state.selectedTab != page) {
            state = state.copy(selectedTab = page)
        }
    }

    private fun updateDirectorySearchQuery(query: String) {
        state = state.copy(directorySearchQuery = query)
    }

    private fun addNewDirectory() {
        val newDirectory = Directory(
            id = System.currentTimeMillis().toString(),
            name = "Новый справочник",
            items = emptyList()
        )
        state = state.copy(
            directories = state.directories + newDirectory,
            expandedDirectories = state.expandedDirectories + newDirectory.id
        )
    }

    private fun toggleDirectoryExpanded(directoryId: String) {
        val newExpanded = if (state.expandedDirectories.contains(directoryId)) {
            state.expandedDirectories - directoryId
        } else {
            state.expandedDirectories + directoryId
        }
        state = state.copy(expandedDirectories = newExpanded)
    }

    private fun startAddingNewItem(directoryId: String) {
        state = state.copy(
            addingNewItemDirectoryId = directoryId,
            newItemText = ""
        )
    }

    private fun updateNewItemText(text: String) {
        state = state.copy(newItemText = text)
    }

    private fun confirmNewItem(directoryId: String) {
        if (state.newItemText.isNotBlank()) {
            val updatedDirectories = state.directories.map { directory ->
                if (directory.id == directoryId) {
                    directory.copy(items = directory.items + state.newItemText)
                } else {
                    directory
                }
            }
            state = state.copy(
                directories = updatedDirectories,
                addingNewItemDirectoryId = null,
                newItemText = ""
            )
        }
    }

    private fun cancelNewItem(directoryId: String) {
        state = state.copy(
            addingNewItemDirectoryId = null,
            newItemText = ""
        )
    }

    private fun deleteDirectory(directoryId: String) {
        state = state.copy(
            directories = state.directories.filter { it.id != directoryId },
            expandedDirectories = state.expandedDirectories - directoryId
        )
    }

    private fun selectBackupItem(item: BackupItem) {
        state = state.copy(
            selectedBackupItem = item,
            isBackupDetailsExpanded = true
        )
    }

    private fun updateBackupSort(sortType: BackupSortType) {
        state = state.copy(backupSortType = sortType)
    }

    private fun collapseBackupDetails() {
        state = state.copy(isBackupDetailsExpanded = false)
    }
}
