package be.appwise.core.extensions.activity

import android.content.pm.ActivityInfo
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    addToBackstack: Boolean,
    TAG: String? = null,
    id: Int,
    fragmentToDelete: Fragment? = null
) {
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    if (fragmentToDelete != null)
        fragmentTransaction.remove(fragmentToDelete)
    fragmentTransaction.replace(id, fragment, TAG)
    if (addToBackstack) {
        fragmentTransaction.addToBackStack(TAG)
    }
    try {
        fragmentTransaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun AppCompatActivity.removeFragmentByTag(tag: String): Boolean {
    return removeFragment(supportFragmentManager.findFragmentByTag(tag))
}

fun AppCompatActivity.removeFragmentByID(@IdRes containerID: Int): Boolean {
    return removeFragment(supportFragmentManager.findFragmentById(containerID))
}

fun AppCompatActivity.removeFragment(fragment: Fragment?): Boolean {
    fragment?.let {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
        return true
    } ?: return false
}

fun AppCompatActivity.isPortrait() = requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

