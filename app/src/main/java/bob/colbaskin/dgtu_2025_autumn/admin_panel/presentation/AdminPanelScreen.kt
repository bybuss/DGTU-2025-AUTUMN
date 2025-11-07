package bob.colbaskin.dgtu_2025_autumn.admin_panel.presentation

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.dgtu_2025_autumn.R
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.Author
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.BackupItem
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.BackupSortType
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.ChangeDescription
import bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models.Directory
import bob.colbaskin.dgtu_2025_autumn.admin_panel.presentation.custom_table.EditableSwipeableTable
import bob.colbaskin.dgtu_2025_autumn.common.UiState
import bob.colbaskin.dgtu_2025_autumn.common.design_system.ErrorScreen
import bob.colbaskin.dgtu_2025_autumn.common.design_system.LoadingScreen
import bob.colbaskin.dgtu_2025_autumn.common.design_system.theme.DGTU2025AUTUMNTheme
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun AdminPanelScreenRoot(
    navController: NavHostController,
    viewModel: AdminPanelViewModel = hiltViewModel(),
    userRole: Role?
) {
    AdminPanelScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun AdminPanelScreen (
    state: AdminPanelState,
    onAction: (AdminPanelAction) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = state.selectedTab)
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.selectedTab) {
        if (pagerState.currentPage != state.selectedTab) {
            pagerState.animateScrollToPage(state.selectedTab)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.selectedTab)
            onAction(AdminPanelAction.PageChanged(pagerState.currentPage))
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page != state.selectedTab) {
                onAction(AdminPanelAction.TabSelected(page))
            }
        }
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(verticalAlignment = Alignment.Bottom) {
            Icon(
                painter = painterResource(R.drawable.trk_logo_transparent),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Text(
                "Аналитика",
                fontWeight = W700,
                fontSize = 19.sp,
                color = Color(0xFF101828)
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        CustomTabRow(
            selectedTab = state.selectedTab,
            onTabSelected = { index ->
                scope.launch {
                    pagerState.animateScrollToPage(index)
                    onAction(AdminPanelAction.TabSelected(index))
                }
            }
        )

        HorizontalPager(
            state = pagerState,
            count = 3,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) { page ->
            when (page) {
                0 -> UsersScreen(state = state, onAction = onAction)
                1 -> BackupScreen(state = state, onAction = onAction)
                2 -> ManualScreen(state = state, onAction = onAction)
                else -> UsersScreen(state = state, onAction = onAction)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Пользователи", "Бэкапы", "Справочники")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFFF3F3F4))
            .padding(1.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabs.forEachIndexed { index, title ->
            CustomTab(
                title = title,
                isSelected = selectedTab == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@Composable
private fun CustomTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFFF4F12) else Color(0xFFF3F3F4)

    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = W500
        )
    }
}

@Composable
fun UsersScreen(
    state: AdminPanelState,
    onAction: (AdminPanelAction) -> Unit
) {
    LaunchedEffect(true) {
        onAction(AdminPanelAction.GetAllUsers)
        onAction(AdminPanelAction.GetAllUnVerifyUsers)
    }

    val allUnVerifyUsers = state.unVerifyUserState
    val allUserSate = state.allUserState

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Требуют верификации",
            fontWeight = W500,
            fontSize = 16.sp
        )
        when (allUnVerifyUsers) {
            is UiState.Error -> ErrorScreen(
                message = "Попробуйте снова!",
                onError = { onAction(AdminPanelAction.GetAllUnVerifyUsers) }
            )
            UiState.Loading -> LoadingScreen()
            is UiState.Success -> {
                EditableSwipeableTable(
                    users = allUnVerifyUsers.data,
                    onUserUpdated = {  },
                    onUserDeleted = { user -> onAction(AdminPanelAction.DeleteUserById(user.id)) },
                    onUserVerified = { user -> onAction(AdminPanelAction.VerifyUser(user.id, Role.USER)) },
                    state = state,
                    onAction = onAction
                )
            }
        }

        Text(
            "Все пользователи",
            fontWeight = W500,
            fontSize = 16.sp
        )
        when (allUserSate) {
            is UiState.Error -> ErrorScreen(
                message = "Попробуйте снова!",
                onError = { onAction(AdminPanelAction.GetAllUsers) }
            )
            UiState.Loading -> LoadingScreen()
            is UiState.Success -> {
                EditableSwipeableTable(
                    users = allUserSate.data,
                    onUserUpdated = {  },
                    onUserDeleted = { user -> onAction(AdminPanelAction.DeleteUserById(user.id)) },
                    onUserVerified = {  },
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun BackupScreen(
    state: AdminPanelState,
    onAction: (AdminPanelAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F7))
    ) {
        HeaderWithSort(
            sortType = state.backupSortType,
            onSortChanged = { sortType ->
                onAction(AdminPanelAction.UpdateBackupSort(sortType))
            }
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            BackupTable(
                backupItems = state.backupItems,
                selectedItem = state.selectedBackupItem,
                onItemSelected = { item ->
                    onAction(AdminPanelAction.SelectBackupItem(item))
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))
            BackupDetails(
                selectedItem = state.selectedBackupItem,
                isExpanded = state.isBackupDetailsExpanded,
                onCollapse = { onAction(AdminPanelAction.CollapseBackupDetails) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun HeaderWithSort(
    sortType: BackupSortType,
    onSortChanged: (BackupSortType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Сортировка:",
                color = Color(0xFF878787),
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            var expanded by remember { mutableStateOf(false) }
            Box {
                Row(
                    modifier = Modifier
                        .clickable { expanded = true }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (sortType) {
                            BackupSortType.CHANGE_HISTORY -> "История изменений"
                        },
                        color = Color(0xFF101828),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Sort",
                        tint = Color(0xFF878787)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("История изменений") },
                        onClick = {
                            onSortChanged(BackupSortType.CHANGE_HISTORY)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BackupTable(
    backupItems: List<BackupItem>,
    selectedItem: BackupItem?,
    onItemSelected: (BackupItem) -> Unit,
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
            items(backupItems) { item ->
                BackupTableRow(
                    item = item,
                    isSelected = selectedItem?.id == item.id,
                    onItemSelected = onItemSelected
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
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = "Название проекта",
            color = Color(0xFF101828),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.6f)
        )
        Text(
            text = "Дата изменений",
            color = Color(0xFF101828),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.3f)
        )
    }
}

@Composable
private fun BackupTableRow(
    item: BackupItem,
    isSelected: Boolean,
    onItemSelected: (BackupItem) -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF7700FF).copy(alpha = 0.1f) else Color.White
    val textColor = if (isSelected) Color(0xFF7700FF) else Color(0xFF101828)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onItemSelected(item) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.id.toString(),
            color = textColor,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = item.projectName,
            color = textColor,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.6f)
        )
        Text(
            text = item.changeDate,
            color = textColor,
            fontSize = 14.sp,
            modifier = Modifier.weight(0.3f)
        )
    }
}

@Composable
private fun BackupDetails(
    selectedItem: BackupItem?,
    isExpanded: Boolean,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isExpanded && selectedItem != null,
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
                    text = "Детали изменения",
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

            selectedItem?.let { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    AuthorSection(author = item.author)

                    Spacer(modifier = Modifier.height(24.dp))

                    ChangeDescriptionSection(changeDescription = item.changeDescription)
                }
            }
        }
    }
}

@Composable
private fun AuthorSection(author: Author) {
    Column {
        Text(
            text = "Автор изменения",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF101828),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = author.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF101828),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = author.role,
            fontSize = 14.sp,
            color = Color(0xFF878787),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = author.position,
            fontSize = 14.sp,
            color = Color(0xFF878787)
        )
    }
}

@Composable
private fun ChangeDescriptionSection(changeDescription: ChangeDescription) {
    Column {
        Text(
            text = "Описание изменения",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF101828),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = changeDescription.title,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color(0xFF101828),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Было",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF878787),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = changeDescription.before,
                    fontSize = 14.sp,
                    color = Color(0xFF101828)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Стало",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF878787),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = changeDescription.after,
                    fontSize = 14.sp,
                    color = Color(0xFF101828)
                )
            }
        }
    }
}

@Composable
fun ManualScreen(
    state: AdminPanelState,
    onAction: (AdminPanelAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F7))
            .padding(24.dp)
    ) {
        SearchTextField(
            value = state.directorySearchQuery,
            onValueChange = { query ->
                onAction(AdminPanelAction.UpdateDirectorySearchQuery(query))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddDirectoryButton(
            onClick = { onAction(AdminPanelAction.AddNewDirectory) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            items(state.filteredDirectories) { directory ->
                DirectoryItem(
                    directory = directory,
                    isExpanded = state.expandedDirectories.contains(directory.id),
                    isAddingNewItem = state.addingNewItemDirectoryId == directory.id,
                    newItemText = state.newItemText,
                    onToggleExpanded = { onAction(AdminPanelAction.ToggleDirectoryExpanded(directory.id)) },
                    onStartAddingNewItem = { onAction(AdminPanelAction.StartAddingNewItem(directory.id)) },
                    onUpdateNewItemText = { text -> onAction(AdminPanelAction.UpdateNewItemText(text)) },
                    onConfirmNewItem = { onAction(AdminPanelAction.ConfirmNewItem(directory.id)) },
                    onCancelNewItem = { onAction(AdminPanelAction.CancelNewItem(directory.id)) },
                    onDeleteDirectory = { onAction(AdminPanelAction.DeleteDirectory(directory.id)) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
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
                                "Поиск",
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
private fun AddDirectoryButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0xFF7700FF), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 20.dp),
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
                "Добавить справочник",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun DirectoryItem(
    directory: Directory,
    isExpanded: Boolean,
    isAddingNewItem: Boolean,
    newItemText: String,
    onToggleExpanded: () -> Unit,
    onStartAddingNewItem: () -> Unit,
    onUpdateNewItemText: (String) -> Unit,
    onConfirmNewItem: () -> Unit,
    onCancelNewItem: () -> Unit,
    onDeleteDirectory: () -> Unit
) {
    var rotation by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isExpanded) {
        rotation = if (isExpanded) 180f else 0f
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpanded() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = directory.name,
                    color = Color(0xFF101828),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete directory",
                        tint = Color(0xFF878787),
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onDeleteDirectory() }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = Color(0xFF878787),
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(rotation)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 300)
                ) + fadeOut(
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    directory.items.forEachIndexed { index, item ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    delayMillis = index * 50
                                )
                            )
                        ) {
                            Text(
                                text = item,
                                color = Color(0xFF101828),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }

                    if (isAddingNewItem) {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + expandVertically()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = newItemText,
                                    onValueChange = onUpdateNewItemText,
                                    modifier = Modifier.weight(1f),
                                    placeholder = {
                                        Text(
                                            "Введите название",
                                            color = Color(0xFF878787),
                                            fontSize = 14.sp
                                        )
                                    },
                                    singleLine = true,
                                    textStyle = LocalTextStyle.current.copy(
                                        fontSize = 14.sp
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Confirm",
                                    tint = Color(0xFF7700FF),
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable { onConfirmNewItem() }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cancel",
                                    tint = Color(0xFF878787),
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable { onCancelNewItem() }
                                )
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onStartAddingNewItem() }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color(0xFF7700FF),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Добавить",
                                color = Color(0xFF7700FF),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AdminPanelScreenPreview() {
    DGTU2025AUTUMNTheme {
        AdminPanelScreen(
            state = AdminPanelState(),
            onAction = {}
        )
    }
}