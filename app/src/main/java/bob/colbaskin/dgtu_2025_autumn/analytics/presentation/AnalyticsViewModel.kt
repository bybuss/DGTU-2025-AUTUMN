package bob.colbaskin.dgtu_2025_autumn.analytics.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(

): ViewModel() {
    var state by mutableStateOf(AnalyticsState())
        private set

    fun onAction(action: AnalyticsAction) {
        when (action) {

        }
    }
}
