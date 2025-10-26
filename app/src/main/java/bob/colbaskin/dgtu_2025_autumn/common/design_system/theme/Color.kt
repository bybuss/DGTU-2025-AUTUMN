package bob.colbaskin.dgtu_2025_autumn.common.design_system.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val purple: Color,
    val orange: Color,
    val lightBlue: Color,
    val lavander: Color
)

val LocalColors = compositionLocalOf { lightColors }

val lightColors = AppColors(
    purple = Color(0xFF7700FF),
    orange = Color(0xFFFF4F12),
    lightBlue = Color(0xFF749FD6),
    lavander = Color(0xFFE8D2ED)
)

val darkColors  = AppColors(
    purple = Color(0xFF7700FF),
    orange = Color(0xFFFF4F12),
    lightBlue = Color(0xFF749FD6),
    lavander = Color(0xFFE8D2ED)
)
