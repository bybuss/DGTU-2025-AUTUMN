package bob.colbaskin.dgtu_2025_autumn.admin_panel.presentation.custom_table

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bob.colbaskin.dgtu_2025_autumn.R
import bob.colbaskin.dgtu_2025_autumn.admin_panel.presentation.AdminPanelAction
import bob.colbaskin.dgtu_2025_autumn.admin_panel.presentation.AdminPanelState
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.domain.models.User
import kotlinx.coroutines.launch

@Composable
fun EditableSwipeableTable(
    users: List<User>,
    onUserUpdated: (User) -> Unit,
    onUserDeleted: (User) -> Unit,
    onUserVerified: (User) -> Unit,
    state: AdminPanelState,
    onAction: (AdminPanelAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
    ) {
        TableHeader(
            state = state,
            onAction = onAction
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                //1.border(1.dp, Color(0xFFCBCBCB))
        ) {
            items(
                items = users,
                key = { it.id }
            ) { user ->
                SwipeableEditableTableRow(
                    user = user,
                    onUserUpdated = onUserUpdated,
                    onUserDeleted = onUserDeleted,
                    onUserVerified = onUserVerified,
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun TableHeader(
    state: AdminPanelState,
    onAction: (AdminPanelAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.border(1.dp, Color(0xFFCBCBCB))
            .background(Color.White)
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "ID",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(0.1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "ФИО",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(0.4f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Email",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SwipeableEditableTableRow(
    user: User,
    onUserUpdated: (User) -> Unit,
    onUserDeleted: (User) -> Unit,
    onUserVerified: (User) -> Unit,
    state: AdminPanelState,
    onAction: (AdminPanelAction) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showVerifyDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    showDeleteDialog = true
                    false
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    showVerifyDialog = true
                    false
                }
                else -> false
            }
        }
    )

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Подтверждение удаления") },
            text = { Text("Вы уверены, что хотите удалить пользователя ${user.name}?") },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            onUserDeleted(user)
                            showDeleteDialog = false
                            swipeToDismissState.reset()
                        }
                    }
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        scope.launch {
                            showDeleteDialog = false
                            swipeToDismissState.reset()
                        }
                    }
                ) {
                    Text("Отмена")
                }
            }
        )
    }

    if (showVerifyDialog) {
        AlertDialog(
            onDismissRequest = { showVerifyDialog = false },
            title = { Text("Подтверждение верификации") },
            text = { Text("Вы уверены, что хотите верифицировать пользователя ${user.name}?") },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            onUserVerified(user)
                            showVerifyDialog = false
                            swipeToDismissState.reset()
                        }
                    }
                ) {
                    Text("Верифицировать")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        scope.launch {
                            showVerifyDialog = false
                            swipeToDismissState.reset()
                        }
                    }
                ) {
                    Text("Отмена")
                }
            }
        )
    }

    SwipeToDismissBox(
        state = swipeToDismissState,
        backgroundContent = {
            val (color, icon, alignment) = when (swipeToDismissState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> Triple(Color(0xFFEDEDED), R.drawable.trash, Alignment.CenterEnd)
                SwipeToDismissBoxValue.StartToEnd -> Triple(Color(0xFFEDEDED), R.drawable.checks, Alignment.CenterStart)
                else -> Triple(Color(0xFFEDEDED), R.drawable.trash, Alignment.CenterEnd)
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(16.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = when (swipeToDismissState.dismissDirection) {
                        SwipeToDismissBoxValue.EndToStart -> "Delete"
                        SwipeToDismissBoxValue.StartToEnd -> "Verify"
                        else -> ""
                    },
                    tint = if (icon == R.drawable.trash) Color(0xFFFF0000) else Color(0xFF7700FF)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = user.id.toString(),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = user.name,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = user.email,
                    modifier = Modifier.weight(1f)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = { onAction(AdminPanelAction.UpdateName(it)) },
                        label = { Text("ФИО") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onAction(AdminPanelAction.UpdateEmail(it)) },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Роль",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Role.entries.filter { it != Role.NOT_VERIFY }.forEach { role ->
                                val isSelected = role == state.role
                                Button(
                                    onClick = { onAction(AdminPanelAction.UpdateRole(newRole = role)) },
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isSelected) {
                                            Color(0xFF749FD6)
                                        } else {
                                            Color.Transparent
                                        },
                                        contentColor = if (isSelected) Color.Blue else Color.Gray
                                    ),
                                    border = BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) Color.Blue else Color.Gray
                                    )
                                ) {
                                    Text(
                                        text = when (role) {
                                            Role.NOT_VERIFY -> "Не верифицирован"
                                            Role.USER -> "Пользовтель"
                                            Role.ANALYST -> "Аналитик"
                                            Role.ADMIN -> "Администратор"
                                        },
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                        Column {
                            Text(text = "Сменить пароль")
                            OutlinedTextField(
                                value = state.password,
                                onValueChange = { onAction(AdminPanelAction.UpdatePassword(it)) },
                                label = { Text("Пароль") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Button(
                        onClick = {
                            val updatedUser = user.copy(
                                name = state.name,
                                email = state.email
                            )
                            onUserUpdated(updatedUser)
                            onAction(AdminPanelAction.VerifyUser(userId = user.id, role = state.role))
                            expanded = false
                        }
                    ) {
                        Text("Сохранить")
                    }
                }
            }
        }
    }
}
