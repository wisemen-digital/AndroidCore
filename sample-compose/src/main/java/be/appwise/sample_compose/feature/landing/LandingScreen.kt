package be.appwise.sample_compose.feature.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import be.appwise.sample_compose.feature.destinations.OverviewButtonsDestination
import be.appwise.sample_compose.feature.destinations.OverviewCalendarDestination
import be.appwise.sample_compose.feature.destinations.OverviewEditTextDestination
import be.appwise.sample_compose.feature.navigation.MainNavGraph
import be.appwise.ui.Buttons
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun LandingScreenLayout(onAction: (LandingUiAction) -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Buttons.Primary(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(LandingUiAction.ToButtons) },
            text = "To Buttons"
        )
        Buttons.Primary(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(LandingUiAction.ToEditText) },
            text = "To Edit Text"
        )
        Buttons.Primary(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(LandingUiAction.ToCalendar) },
            text = "To Calendar"
        )

    }
}

@Destination
@MainNavGraph(start = true)
@Composable
fun LandingScreen(
    navController: NavController,
    viewModel: LandingViewModel = LandingViewModel()
) {

    LaunchedEffect(viewModel) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is LandingUiEvent.NavigateToButtons -> navController.navigate(
                    OverviewButtonsDestination
                )

                is LandingUiEvent.NavigateToCalendar -> navController.navigate(
                    OverviewCalendarDestination
                )

                is LandingUiEvent.NavigateToEditText -> navController.navigate(
                    OverviewEditTextDestination
                )
            }
        }
    }

    LandingScreenLayout(onAction = viewModel::onAction)
}