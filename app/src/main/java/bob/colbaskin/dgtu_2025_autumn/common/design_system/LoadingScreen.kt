package bob.colbaskin.dgtu_2025_autumn.common.design_system

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import bob.colbaskin.dgtu_2025_autumn.R

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Lottie(lottieJson = R.raw.loading, speed = 3f)
    }
}
