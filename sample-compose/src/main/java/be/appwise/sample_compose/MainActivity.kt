package be.appwise.sample_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import be.appwise.sample_compose.feature.NavGraphs
import com.example.compose.CoreDemoTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoreDemoTheme {

                val navController = rememberNavController()

                Scaffold { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(paddingValues)
                    ) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController
                        )
                    }
                }
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//
//                    DestinationsNavHost(navGraph =Na)
//                    OverviewCalendar()
////                    OverviewButtons()
////                    OverviewEditText()
//                }
            }
        }
    }
}
