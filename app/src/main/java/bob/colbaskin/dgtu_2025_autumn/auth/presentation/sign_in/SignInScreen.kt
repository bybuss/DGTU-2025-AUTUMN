package bob.colbaskin.dgtu_2025_autumn.auth.presentation.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.dgtu_2025_autumn.auth.presentation.sign_up.SignUpAction
import bob.colbaskin.dgtu_2025_autumn.common.UiState
import bob.colbaskin.dgtu_2025_autumn.common.design_system.theme.CustomTheme
import bob.colbaskin.dgtu_2025_autumn.navigation.Screens
import bob.colbaskin.dgtu_2025_autumn.navigation.graphs.Graphs
import kotlinx.coroutines.launch

@Composable
fun SignInScreenRoot(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val authState = state.authState
    val scope = rememberCoroutineScope()

    SignInScreen(
        state = state,
        onAction = { action ->
            when (action) {
                SignInAction.SignIn -> {
                    when (authState) {
                        is UiState.Success -> { navController.navigate(Graphs.Main) }
                        is UiState.Error -> {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    authState.title,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                        else -> {}
                    }
                }
                SignInAction.SignUp -> { navController.navigate(Screens.SignUp) }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun SignInScreen(
    state: SignInState,
    onAction: (SignInAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    var showPassword by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .verticalScroll(scrollState)
                .imePadding()
        ) {
            Spacer(modifier = Modifier.height(13.dp) )
            Column {
                Text(
                    text = "Вход в аккаунт",
                    modifier = Modifier,
                    color = Color.Black,
                    fontWeight = W700,
                    fontSize = 24.sp
                )
                Text(
                    text = "Авторизируйтесь, чтобы продолжить",
                    modifier = Modifier,
                    color = Color(0xFF878787),
                    fontWeight = W500,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(13.dp))
            Column {
                Column {
                    Text(
                        "Email",
                        fontWeight = W500,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onAction(SignInAction.UpdateEmail(it)) },
                        //label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = !state.isEmailValid,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF6F6F6),
                            unfocusedContainerColor = Color(0xFFF6F6F6),
                            disabledContainerColor = Color(0xFFF6F6F6),
                            errorContainerColor = Color(0xFFF6F6F6),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                            errorBorderColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
                Column {
                    Text(
                        "Пароль",
                        fontWeight = W500,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { onAction(SignInAction.UpdatePassword(it)) },
                        //label = { Text("Пароль") },
                        visualTransformation = if (showPassword) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword)
                                        Icons.Filled.Visibility
                                    else
                                        Icons.Filled.VisibilityOff,
                                    contentDescription = if (showPassword) "Hide password"
                                    else "Show password"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF6F6F6),
                            unfocusedContainerColor = Color(0xFFF6F6F6),
                            disabledContainerColor = Color(0xFFF6F6F6),
                            errorContainerColor = Color(0xFFF6F6F6),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                            errorBorderColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(33.dp))
                Button(
                    onClick = { onAction(SignInAction.SignIn) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.isFormValid,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = CustomTheme.colors.purple
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Войти")
                    }
                }
                Spacer(modifier = Modifier.height(33.dp))
                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = { onAction(SignInAction.SignUp) }
                        )
                ) {
                    Text("Уже есть аккаунт?")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Зарегистрироваться",
                        color = CustomTheme.colors.purple
                    )
                }
            }
        }
    }
}
