package be.appwise.core.ui.base

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import be.appwise.core.R

open class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    /**
     * The viewModel that will be used for this Activity
     */
    protected lateinit var viewModel: VM

    /**
     * A default implementation to configure a toolbar
     * Included is an implementation to handle the onClick on the back icon.
     * Override 'onToolbarNavigationIconClicked()' to create your own implementation
     *
     * @param toolbar The toolbar object needed to configure
     * @param showBackIcon A boolean to determine if a back icon should be shown (defaults to false)
     * @param toolbarTitleRes A String resource to give the toolbar a title (defaults to app name)
     * @param drawableRes A drawable resource to give the toolbar an icon to navigate back (defaults to an arrow to the left)
     */
    protected fun configureToolbar(toolbar: Toolbar, showBackIcon: Boolean = false,
        @StringRes toolbarTitleRes: Int = R.string.app_name,
        @DrawableRes drawableRes: Int = R.drawable.ic_navigation_back) {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setTitle(toolbarTitleRes)
            if (showBackIcon) {
                it.setDisplayHomeAsUpEnabled(true)
                it.setHomeAsUpIndicator(drawableRes)
                toolbar.setNavigationOnClickListener { onToolbarNavigationIconClicked() }
            }
        }
    }

    /**
     * Standard implementation to exit the activity upon pressing on the toolbar's "Home Icon"
     * Can be overridden to have a project-specific implementation
     */
    open fun onToolbarNavigationIconClicked() {
        onBackPressed()
    }
}