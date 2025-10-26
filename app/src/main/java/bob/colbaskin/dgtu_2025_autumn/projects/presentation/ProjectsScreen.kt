package bob.colbaskin.dgtu_2025_autumn.projects.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.dgtu_2025_autumn.projects.domain.models.Project

@Composable
fun ProjectsScreenRoot(
    navController: NavHostController,
    viewModel: ProjectsViewModel = hiltViewModel()
) {
    ProjectsScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ProjectsScreen(
    state: ProjectsState,
    onAction: (ProjectsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F7))
    ) {
        HeaderWithSearch(
            searchQuery = state.searchQuery,
            onSearchQueryChange = { query ->
                onAction(ProjectsAction.UpdateSearchQuery(query))
            },
            onAddClick = { onAction(ProjectsAction.AddNewProject) }
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ProjectsTable(
                projects = state.getFilteredProjects,
                selectedProject = state.selectedProject,
                onProjectSelected = { project ->
                    onAction(ProjectsAction.SelectProject(project))
                },
                onProjectDelete = { projectId ->
                    onAction(ProjectsAction.DeleteProject(projectId))
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            ProjectDetails(
                project = state.editingProject,
                isExpanded = state.isProjectDetailsExpanded,
                onFieldUpdate = { projectId, field, value ->
                    onAction(ProjectsAction.UpdateProjectField(projectId, field, value))
                },
                onCollapse = { onAction(ProjectsAction.CollapseProjectDetails) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProjectDetails(
    project: Project?,
    isExpanded: Boolean,
    onFieldUpdate: (Int, String, String) -> Unit,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isExpanded && project != null,
        enter = fadeIn() + expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(durationMillis = 300)
        ),
        exit = fadeOut() + shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Детали проекта",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF101828)
                )
                IconButton(onClick = onCollapse) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Свернуть",
                        tint = Color(0xFF878787)
                    )
                }
            }

            project?.let { currentProject ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    ProjectFields(
                        project = currentProject,
                        onFieldUpdate = onFieldUpdate
                    )
                }
            }
        }
    }
}

@Composable
fun ProjectFields(
    project: Project,
    onFieldUpdate: (Int, String, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Сегмент",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF101828),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = project.segment,
                    onValueChange = { onFieldUpdate(project.id, "segment", it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "ИНН",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF101828),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = project.inn,
                    onValueChange = { onFieldUpdate(project.id, "inn", it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Менеджер",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF101828),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = project.manager,
                    onValueChange = { onFieldUpdate(project.id, "manager", it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Организация",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF101828),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = project.organization,
                    onValueChange = { onFieldUpdate(project.id, "organization", it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Услуга",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF101828),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = project.service,
                    onValueChange = { onFieldUpdate(project.id, "service", it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Этап",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF101828),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = project.stage,
                    onValueChange = { onFieldUpdate(project.id, "stage", it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Сумма",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF101828),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = project.amount,
                    onValueChange = { onFieldUpdate(project.id, "amount", it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Вероятность",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF101828),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = project.probability,
                    onValueChange = { onFieldUpdate(project.id, "probability", it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }
    }
}

@Composable
private fun HeaderWithSearch(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Аналитика",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF101828)
            )

            Box(
                modifier = Modifier
                    .height(48.dp)
                    .background(Color(0xFF7700FF), RoundedCornerShape(8.dp))
                    .clickable { onAddClick() }
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Добавить проект",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SearchTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange
        )
    }
}

@Composable
private fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFCBCBCB), RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF878787),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        color = Color(0xFF101828),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                "Поиск по названию",
                                color = Color(0xFF878787),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }
    }
}

@Composable
private fun ProjectsTable(
    projects: List<Project>,
    selectedProject: Project?,
    onProjectSelected: (Project) -> Unit,
    onProjectDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(8.dp))
    ) {
        TableHeader()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(projects) { project ->
                ProjectTableRow(
                    project = project,
                    isSelected = selectedProject?.id == project.id,
                    onProjectSelected = onProjectSelected,
                    onProjectDelete = onProjectDelete
                )
            }
        }
    }
}

@Composable
private fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF3F3F4))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "ID",
            color = Color(0xFF101828),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Название проекта",
            color = Color(0xFF101828),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.7f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Год",
            color = Color(0xFF101828),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProjectTableRow(
    project: Project,
    isSelected: Boolean,
    onProjectSelected: (Project) -> Unit,
    onProjectDelete: (Int) -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF7700FF).copy(alpha = 0.1f) else Color.White
    val textColor = if (isSelected) Color(0xFF7700FF) else Color(0xFF101828)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onProjectSelected(project) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = project.id.toString(),
            color = textColor,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = project.name,
            color = textColor,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.6f),
            textAlign = TextAlign.Center
        )
        Text(
            text = project.year.toString(),
            color = textColor,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = { onProjectDelete(project.id) },
            modifier = Modifier.weight(0.1f)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color(0xFF878787)
            )
        }
    }
}