package bob.colbaskin.dgtu_2025_autumn.projects.presentation

import bob.colbaskin.dgtu_2025_autumn.projects.domain.models.Project

sealed interface ProjectsAction {
    data class SelectProject(val project: Project) : ProjectsAction
    data class UpdateProjectField(val projectId: Int, val field: String, val value: String) : ProjectsAction
    object CollapseProjectDetails : ProjectsAction
    object AddNewProject : ProjectsAction
    data class DeleteProject(val projectId: Int) : ProjectsAction
    data class UpdateSearchQuery(val query: String) : ProjectsAction
}
