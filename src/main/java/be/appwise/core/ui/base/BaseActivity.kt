package be.appwise.core.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import be.appwise.core.extensions.coroutine.UiLifecycleScope

abstract class BaseActivity: AppCompatActivity() {

    val uiScope = UiLifecycleScope()

    //TODO: is this override of the correct onCreate? Typically we don't use the one with 'persistentState'
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        lifecycle.addObserver(uiScope)
    }
}