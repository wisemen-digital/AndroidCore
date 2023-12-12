package be.appwise.compose.core.ui.base

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import be.appwise.compose.core.ui.base.snackbar.BaseSnackbar
import be.appwise.compose.core.ui.base.snackbar.Empty
import be.appwise.compose.core.ui.base.snackbar.Error
import be.appwise.compose.core.ui.base.snackbar.SnackbarMessage
import be.appwise.core.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseComposeViewModel: BaseViewModel() {
    // Snackbar Handling
    private var snackbarMessageState by mutableStateOf<SnackbarMessage>(Empty)

    override fun vmScopeWithCustomExceptionHandler(onError: (error: Throwable) -> Unit): CoroutineScope {
        return super.vmScopeWithCustomExceptionHandler {
            Log.w(this::class.simpleName, "Exception caught: ", it)
            snackbarMessageState = Error(it.message)
        }
    }

    @Composable
    fun createSnackBarHost() {
        val snackBarHostState = remember {
            SnackbarHostState()
        }

        val context = LocalContext.current

        LaunchedEffect(key1 = snackbarMessageState) {
            if (snackbarMessageState !is Empty) {
                snackBarHostState.showSnackbar(snackbarMessageState.getMessage(context) ?: "Unknown error")
                setCoroutineException(null)
                snackbarMessageState = Empty
            }
        }

        SnackbarHost(hostState = snackBarHostState) { snackBarData ->
            AnimatedVisibility(visible = snackbarMessageState.isNotEmpty()) {
                BaseSnackbar(
                    snackbarMessageState.icon,
                    snackBarData.visuals.message,
                    containerColor = snackbarMessageState.containerColor
                        ?: Color.Transparent
                )
            }
        }
    }


    fun showSnackbar(snackbarMessage: SnackbarMessage) = vmScope.launch {
        snackbarMessageState = snackbarMessage
    }

    fun showSnackbar(message: String?) = vmScope.launch {
        snackbarMessageState = Error(message = message ?: return@launch)
    }
}
