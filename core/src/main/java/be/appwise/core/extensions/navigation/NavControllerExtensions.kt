package be.appwise.core.extensions.navigation

import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun NavController.tryNavigate(directions: NavDirections, navOptions: NavOptions? = null){
    tryAction {
        navigate(directions, navOptions)
    }
}

fun NavController.tryNavigate(directions: NavDirections, extras: Navigator.Extras){
    tryAction {
        navigate(directions, extras)
    }
}

fun NavController.tryNavigate(@IdRes id: Int, bundle: Bundle? = null, navOptions: NavOptions? = null, extras: Navigator.Extras? = null){
    tryAction {
        navigate(id, bundle, navOptions, extras)
    }
}

fun NavController.tryNavigate(uri: Uri, navOptions: NavOptions? = null, extras: Navigator.Extras? = null){
    tryAction {
        navigate(uri, navOptions, extras)
    }
}

fun NavController.tryNavigate(navDeepLinkRequest: NavDeepLinkRequest, navOptions: NavOptions? = null, extras: Navigator.Extras? = null){
    tryAction {
        navigate(navDeepLinkRequest, navOptions, extras)
    }
}

private fun tryAction(action: () -> Unit){
    try {
        action()
    } catch (ex: IllegalArgumentException){
        ex.printStackTrace()
    }
}