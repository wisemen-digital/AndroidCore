package be.appwise.core.ui.base

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import be.appwise.core.R
import be.appwise.core.extensions.fragment.snackBar
import be.appwise.core.extensions.logging.loge

open class BaseFragment : Fragment() {
    companion object {
        const val SHOW_BACK_ICON = "SHOW_BACK_ICON"
    }

    lateinit var parentActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentActivity = requireActivity() as AppCompatActivity
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in childFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    protected fun configureToolbar(toolbar: Toolbar, color: Int? = null,
        showTitle: Boolean = true, showBackIcon: Boolean = false) {
        parentActivity.setSupportActionBar(toolbar)
        if (showBackIcon) {
            parentActivity.supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setHomeAsUpIndicator(R.drawable.ic_navigation_back)
            }
            color?.let { toolbar.navigationIcon?.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.SRC_IN) }
            if (!showTitle) {
                toolbar.title = null
            }
            toolbar.setNavigationOnClickListener { parentActivity.onBackPressed() }
        }
    }

    fun tintMenuIcons(menu: Menu, color: Int) {
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            val wrapDrawable = DrawableCompat.wrap(
                item.icon)
            DrawableCompat.setTint(wrapDrawable, color)
            item.icon = wrapDrawable
        }
    }

    open fun onError(throwable: Throwable) {
        snackBar(throwable.message ?: getString(R.string.error_default))
        loge(null, throwable)
    }
}