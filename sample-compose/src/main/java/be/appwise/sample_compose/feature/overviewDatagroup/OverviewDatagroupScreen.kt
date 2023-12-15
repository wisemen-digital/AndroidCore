package be.appwise.sample_compose.feature.overviewDatagroup

import CoreDemoTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import be.appwise.sample_compose.R
import be.appwise.sample_compose.data.entity.Datagroup
import be.appwise.sample_compose.data.mock.MOCK_DATAGROUP
import be.appwise.sample_compose.feature.destinations.LandingScreenDestination
import be.appwise.sample_compose.feature.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.wiselab.groupdatarow.Datagroup

@Composable
fun OverviewDatagroupLayout(onAction: (OverviewDatagroupUiAction) -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconButton(
            onClick = {
                onAction(OverviewDatagroupUiAction.Back)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.ChevronLeft,
                contentDescription = stringResource(R.string.back)
            )
        }

        Datagroup(datarowGroup = Datagroup.MOCK_DATAGROUP)

        Datagroup(
            datarowGroup = Datagroup.MOCK_DATAGROUP,
            divider = {
                Divider(
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            cardColors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            cardShape = RoundedCornerShape(15.dp)
        )
    }
}

@Destination
@MainNavGraph
@Composable
fun OverviewDatagroup(
    navController: NavController,
    viewModel: OverviewDatagroupViewModel = OverviewDatagroupViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is OverviewDatagroupUiEvent.NavigateBack -> navController.navigate(
                    LandingScreenDestination
                )
            }
        }
    }

    OverviewDatagroupLayout(onAction = viewModel::onAction)
}

@Preview
@Composable
fun OverviewDatagroupPreview() {
    CoreDemoTheme {
        OverviewDatagroupLayout()
    }
}