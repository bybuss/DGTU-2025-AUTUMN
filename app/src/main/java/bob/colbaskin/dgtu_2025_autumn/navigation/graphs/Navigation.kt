package bob.colbaskin.dgtu_2025_autumn.navigation.graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import bob.colbaskin.dgtu_2025_autumn.admin_panel.presentation.AdminPanelScreenRoot
import bob.colbaskin.dgtu_2025_autumn.analytics.presentation.AnalyticsScreenRoot
import bob.colbaskin.dgtu_2025_autumn.auth.presentation.sign_in.SignInScreenRoot
import bob.colbaskin.dgtu_2025_autumn.auth.presentation.sign_up.SignUpScreenRoot
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.OnboardingConfig
import bob.colbaskin.dgtu_2025_autumn.common.user_prefs.data.models.Role
import bob.colbaskin.dgtu_2025_autumn.navigation.Screens
import bob.colbaskin.dgtu_2025_autumn.navigation.animatedTransition
import bob.colbaskin.dgtu_2025_autumn.onboarding.presentation.IntroductionScreen
import bob.colbaskin.dgtu_2025_autumn.onboarding.presentation.WelcomeScreen
import bob.colbaskin.dgtu_2025_autumn.projects.presentation.ProjectsScreenRoot
import bob.colbaskin.dgtu_2025_autumn.reports.ReportsScreen
import bob.colbaskin.dgtu_2025_autumn.waiting_verification.WaitingVerificationScreenRoot


fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
    onboardingStatus: OnboardingConfig,
    snackbarHostState: SnackbarHostState
) {
    navigation<Graphs.Onboarding>(
        startDestination = getStartDestination(onboardingStatus)
    ) {
        animatedTransition<Screens.Welcome> {
            WelcomeScreen (
                onNextScreen = { navController.navigate(Screens.Introduction) {
                    popUpTo(Screens.Welcome) { inclusive = true }
                }}
            )
        }
        animatedTransition<Screens.Introduction> {
            IntroductionScreen (
                onNextScreen = { navController.navigate(Screens.SignIn) {
                    popUpTo(Screens.Introduction) { inclusive = true }
                }}
            )
        }
        animatedTransition<Screens.SignIn> {
            SignInScreenRoot(
                navController = navController,
                snackbarHostState = snackbarHostState
            )
        }
        animatedTransition<Screens.SignUp> {
            SignUpScreenRoot(
                navController = navController,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    userRole: Role? = null
) {
    navigation<Graphs.Main>(
        startDestination = Screens.Dashboard
    ) {
        animatedTransition<Screens.Dashboard> {
            AnalyticsScreenRoot(navController)
        }
        animatedTransition<Screens.Projects> {
            ProjectsScreenRoot(navController = navController)
        }
        animatedTransition<Screens.Rapports> {
            ReportsScreen()
        }
        animatedTransition<Screens.Admin> {
            AdminPanelScreenRoot(
                navController = navController,
                userRole = userRole
            )
        }
    }
}

fun NavGraphBuilder.verificationGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
) {
    navigation<Graphs.Verification>(
        startDestination = Screens.WaitingVerification
    ) {
        animatedTransition<Screens.WaitingVerification> {
            WaitingVerificationScreenRoot(navController)
        }
    }
}

private fun getStartDestination(status: OnboardingConfig) = when (status) {
    OnboardingConfig.NOT_STARTED -> Screens.Welcome
    OnboardingConfig.IN_PROGRESS -> Screens.Introduction
    OnboardingConfig.COMPLETED -> Screens.SignIn
    else -> Screens.Welcome
}
