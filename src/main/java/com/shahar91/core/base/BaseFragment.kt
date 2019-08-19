package com.shahar91.core.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.shahar91.core.extensions.coroutine.UiLifecycleScope

abstract class BaseFragment: Fragment() {

    val uiScope = UiLifecycleScope()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(uiScope)
    }
}