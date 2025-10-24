package bob.colbaskin.dgtu_2025_autumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import bob.colbaskin.dgtu_2025_autumn.common.MainViewModel
import bob.colbaskin.dgtu_2025_autumn.common.UiState
import bob.colbaskin.dgtu_2025_autumn.common.design_system.theme.DGTU2025AUTUMNTheme
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.UserPreferences
import bob.colbaskin.dgtu_2025_autumn.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        var uiState: UiState<UserPreferences> by mutableStateOf(UiState.Loading)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState
                    .onEach { uiState = it }
                    .collect {}
            }
        }

        enableEdgeToEdge()
        setContent {
            DGTU2025AUTUMNTheme {
                val isDarkTheme = isSystemInDarkTheme()
                val insetsController = WindowInsetsControllerCompat(window, window.decorView)

                SideEffect {
                    insetsController.isAppearanceLightStatusBars = !isDarkTheme
                }

                if (uiState is UiState.Success<UserPreferences>) {
                    AppNavHost(uiState = uiState as UiState.Success<UserPreferences>)
                }

            }
        }
    }
}
