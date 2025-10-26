 package bob.colbaskin.dgtu_2025_autumn.projects.presentation

 import bob.colbaskin.dgtu_2025_autumn.projects.domain.models.Project

 data class ProjectsState(
     val projects: List<Project> = emptyList(),
     val selectedProject: Project? = null,
     val isProjectDetailsExpanded: Boolean = false,
     val editingProject: Project? = null,
     val searchQuery: String = "",
     val filteredProjects: List<Project> = emptyList()
 ) {
     val getFilteredProjects: List<Project>
         get() = if (searchQuery.isBlank()) {
             projects
         } else {
             projects.filter {
                 it.name.contains(searchQuery, ignoreCase = true) ||
                         it.organization.contains(searchQuery, ignoreCase = true) ||
                         it.manager.contains(searchQuery, ignoreCase = true)
             }
         }
 }