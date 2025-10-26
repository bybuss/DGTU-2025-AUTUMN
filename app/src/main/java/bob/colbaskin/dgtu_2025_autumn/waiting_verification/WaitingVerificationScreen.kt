package bob.colbaskin.dgtu_2025_autumn.waiting_verification

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import bob.colbaskin.dgtu_2025_autumn.common.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun WaitingVerificationScreenRoot(
    navController: NavController,
    viewModel: WaitingVerificationViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val isChecking by mainViewModel.isCheckingVerification.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            delay(10000)
            mainViewModel.refreshVerificationStatus()
        }
    }

    WaitingVerificationScreen(
        isChecking = isChecking,
        onRefresh = { mainViewModel.refreshVerificationStatus() },
        onLogout = {
            navController.popBackStack()
            viewModel.logout()
        }
    )

}

@Composable
private fun WaitingVerificationScreen(
    isChecking: Boolean,
    onRefresh: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ожидание верификации",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isChecking) {
            CircularProgressIndicator()
            Text("Проверяем статус...", modifier = Modifier.padding(top = 16.dp))
        } else {
            Icon(
                imageVector = Icons.Outlined.HourglassEmpty,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "Ваш аккаунт ожидает подтверждения администратором",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(onClick = onRefresh) {
                Text("Проверить статус")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(onClick = onLogout) {
            Text("Выйти")
        }
    }
}

