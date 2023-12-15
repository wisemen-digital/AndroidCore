package be.appwise.sample_compose.feature.overviewButtons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import be.appwise.sample_compose.R
import be.appwise.sample_compose.feature.destinations.LandingScreenDestination
import be.appwise.sample_compose.feature.navigation.MainNavGraph
import be.appwise.ui.Buttons
import com.example.compose.CoreDemoTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate


@Composable
fun OverviewButtonsLayout(onAction: (OverviewButtonsUiAction) -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconButton(
            onClick = {
                onAction(OverviewButtonsUiAction.Back)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.ChevronLeft,
                contentDescription = stringResource(R.string.to_prev_month)
            )
        }
        Buttons.Primary(
            modifier = Modifier.fillMaxWidth(),
            text = "Primary"
        )
        Buttons.Primary(
            modifier = Modifier.fillMaxWidth(),
            text = "Primary",
            enabled = false
        )
        Buttons.Primary(
            modifier = Modifier.fillMaxWidth(),
            text = "Primary",
            contentColor = Color.Black,
            containerColor = Color.Cyan
        )
        Buttons.Primary(
            modifier = Modifier.fillMaxWidth(),
            text = "Primary",
            leading = {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Check mark"
                )
            }
        )
        Buttons.Primary(
            modifier = Modifier.fillMaxWidth(),
            text = "Primary",
            trailing = {
                Icon(
                    modifier = Modifier.padding(start = 8.dp),
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Check mark"
                )
            }
        )
        Buttons.Primary(
            modifier = Modifier.fillMaxWidth(),
            text = "Primary Different Shape",
            buttonShape = CircleShape
        )

        Buttons.Secondary(
            modifier = Modifier.fillMaxWidth(),
            text = "Secondary"
        )
        Buttons.Secondary(
            modifier = Modifier.fillMaxWidth(),
            text = "Secondary",
            enabled = false
        )

        Buttons.Tertiary(
            modifier = Modifier.fillMaxWidth(),
            text = "Tertiary"
        )
        Buttons.Tertiary(
            modifier = Modifier.fillMaxWidth(),
            text = "Tertiary",
            enabled = false
        )

        Buttons.Delete(
            modifier = Modifier.fillMaxWidth(),
            text = "Delete",
            trailing = {
                Icon(
                    modifier = Modifier.padding(start = 8.dp),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Check mark"
                )
            }
        )
    }
}

@Destination
@MainNavGraph
@Composable
fun OverviewButtons(
    navController: NavController,
    viewModel: OverviewButtonsViewModel = OverviewButtonsViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is OverviewButtonsUiEvent.NavigateBack -> navController.navigate(
                    LandingScreenDestination
                )
            }
        }
    }

    OverviewButtonsLayout(onAction = viewModel::onAction)
}

@Preview
@Composable
fun OverviewButtonsPreview() {
    CoreDemoTheme {
        OverviewButtonsLayout()
    }
}