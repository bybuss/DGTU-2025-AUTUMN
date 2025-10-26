package bob.colbaskin.dgtu_2025_autumn.projects.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.dgtu_2025_autumn.projects.domain.models.Project
import com.google.protobuf.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(ProjectsState())
        private set

    init {
        state = state.copy(
            projects = listOf(
                Project(
                    id = 1,
                    name = "Внедрение системы управленческого учета",
                    year = 2025,
                    segment = "Крупный бизнес",
                    inn = "7701234567",
                    manager = "Зубенко М.П.",
                    organization = "ООО \"Ромашка\"",
                    service = "ИТ-консалтинг",
                    stage = "Реализация",
                    amount = "12 150 000 ₽",
                    probability = "7 300 000 ₽"
                ),
                Project(
                    id = 2,
                    name = "Разработка мобильного приложения",
                    year = 2025,
                    segment = "Средний бизнес",
                    inn = "7707654321",
                    manager = "Иванов И.И.",
                    organization = "ООО \"ТехноЛаб\"",
                    service = "Разработка ПО",
                    stage = "Проектирование",
                    amount = "8 500 000 ₽",
                    probability = "6 000 000 ₽"
                )
            )
        )
    }

    fun onAction(action: ProjectsAction) {
        when (action) {
            is ProjectsAction.SelectProject -> selectProject(action.project)
            is ProjectsAction.UpdateProjectField -> updateProjectField(action.projectId, action.field, action.value)
            is ProjectsAction.CollapseProjectDetails -> collapseProjectDetails()
            is ProjectsAction.AddNewProject -> addNewProject()
            is ProjectsAction.DeleteProject -> deleteProject(action.projectId)
            is ProjectsAction.UpdateSearchQuery -> updateSearchQuery(action.query)
        }
    }

    private fun selectProject(project: Project) {
        state = state.copy(
            selectedProject = project,
            editingProject = project,
            isProjectDetailsExpanded = true
        )
    }

    private fun updateProjectField(projectId: Int, field: String, value: String) {
        viewModelScope.launch {
            val updatedProjects = state.projects.map { project ->
                if (project.id == projectId) {
                    when (field) {
                        "name" -> project.copy(name = value)
                        "segment" -> project.copy(segment = value)
                        "inn" -> project.copy(inn = value)
                        "manager" -> project.copy(manager = value)
                        "organization" -> project.copy(organization = value)
                        "service" -> project.copy(service = value)
                        "stage" -> project.copy(stage = value)
                        "amount" -> project.copy(amount = value)
                        "probability" -> project.copy(probability = value)
                        else -> project
                    }
                } else {
                    project
                }
            }

            state = state.copy(
                projects = updatedProjects,
                editingProject = updatedProjects.find { it.id == projectId }
            )
        }
    }

    private fun collapseProjectDetails() {
        state = state.copy(
            isProjectDetailsExpanded = false,
            selectedProject = null,
            editingProject = null
        )
    }

    private fun addNewProject() {
        viewModelScope.launch {
            val newProject = Project(
                id = (state.projects.maxOfOrNull { it.id } ?: 0) + 1,
                name = "Новый проект",
                year = 2025,
                segment = "",
                inn = "",
                manager = "",
                organization = "",
                service = "",
                stage = "",
                amount = "",
                probability = ""
            )
            state = state.copy(
                projects = state.projects + newProject,
                selectedProject = newProject,
                editingProject = newProject,
                isProjectDetailsExpanded = true
            )
        }
    }

    private fun deleteProject(projectId: Int) {
        viewModelScope.launch {
            state = state.copy(
                projects = state.projects.filter { it.id != projectId },
                selectedProject = null,
                editingProject = null,
                isProjectDetailsExpanded = false
            )
        }
    }

    private fun updateSearchQuery(query: String) {
        state = state.copy(searchQuery = query)
    }
}