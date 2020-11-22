package be.appwise.core.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.appwise.core.R
import be.appwise.core.extensions.fragment.snackBar
import com.orhanobut.logger.Logger

abstract class BaseVMFragment<VM : BaseViewModel> : Fragment() {
    /**
     * The viewModel that will be used for this Activity
     */
    protected lateinit var mViewModel: VM
        private set

    protected abstract fun getViewModel(): Class<VM>

    protected open fun getViewModelFactory(): ViewModelProvider.NewInstanceFactory =
        ViewModelProvider.NewInstanceFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    protected open fun init() {
        mViewModel = ViewModelProvider(this, getViewModelFactory()).get(getViewModel()).apply {
            setDefaultExceptionHandler(::onError)
        }
    }

    open fun onError(throwable: Throwable) {
        snackBar(throwable.message ?: getString(R.string.error_default))
        Logger.t("BaseVMFragment").e(throwable, throwable.message ?: getString(R.string.error_default))
    }
}