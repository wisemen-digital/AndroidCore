package be.appwise.core.ui.base

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import be.appwise.core.R
import be.appwise.core.extensions.activity.snackBar
import com.orhanobut.logger.Logger

open class BaseActivity : AppCompatActivity() {
    /**
     * A default implementation to configure a toolbar
     * Included is an implementation to handle the onClick on the back icon.
     * Override 'onToolbarNavigationIconClicked()' to create your own implementation
     *
     * @param toolbar The toolbar object needed to configure
     * @param toolbarTitleRes A String resource to give the toolbar a title (defaults to app name)
     * @param showBackIcon A boolean to determine if a back icon should be shown (defaults to false)
     * @param backIconDrawableRes A drawable resource to give the toolbar an icon to navigate back (defaults to an arrow to the left)
     * @param backIconColor A color used for the back icon, if no color is provided it will show the default color
     */
    protected fun configureToolbar(
        toolbar: Toolbar,
        @StringRes toolbarTitleRes: Int = R.string.app_name,
        showBackIcon: Boolean = false,
        @DrawableRes backIconDrawableRes: Int = R.drawable.ic_navigation_back,
        backIconColor: Int? = null
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setTitle(toolbarTitleRes)
            if (showBackIcon) {
                it.setDisplayHomeAsUpEnabled(true)
                it.setHomeAsUpIndicator(backIconDrawableRes)
                toolbar.setNavigationOnClickListener { onToolbarNavigationIconClicked() }
                backIconColor?.let { toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, backIconColor)) }
            }
        }
    }

    /**
     * Standard implementation to exit the activity upon pressing on the toolbar's "Home Icon"
     * Can be overridden to have a project(or class)-specific implementation
     */
    open fun onToolbarNavigationIconClicked() {
        onBackPressed()
    }

    open fun onError(throwable: Throwable) {
        snackBar(throwable.message ?: getString(R.string.error_default))
        Logger.t("BaseActivity").e(throwable, throwable.message ?: getString(R.string.error_default))
    }
}