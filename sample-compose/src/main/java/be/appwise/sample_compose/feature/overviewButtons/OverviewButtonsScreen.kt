package be.appwise.sample_compose.feature.overviewButtons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.appwise.sample_compose.feature.navigation.MainNavGraph
import be.appwise.ui.Buttons
import com.example.compose.CoreDemoTheme
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@MainNavGraph
@Composable
fun OverviewButtons() {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

@Preview
@Composable
fun OverviewButtonsPreview() {

    CoreDemoTheme {
        OverviewButtons()
    }
}