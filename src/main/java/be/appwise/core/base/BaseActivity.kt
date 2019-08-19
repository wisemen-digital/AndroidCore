package be.appwise.core.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import be.appwise.core.extensions.coroutine.UiLifecycleScope

abstract class BaseActivity: AppCompatActivity() {

    val uiScope = UiLifecycleScope()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        lifecycle.addObserver(uiScope)
    }
}