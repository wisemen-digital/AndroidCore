package be.appwise.core.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {
    /**
     * The viewModel that will be used for this Activity
     */
    protected lateinit var viewModel: VM
        private set

    protected abstract fun getViewModel(): Class<VM>

    protected open fun getViewModelFactory(): ViewModelProvider.NewInstanceFactory =
        ViewModelProvider.NewInstanceFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    protected open fun init() {
        viewModel = ViewModelProvider(this, getViewModelFactory()).get(getViewModel()).apply {
            setDefaultExceptionHandler(::onError)
        }
    }
}
