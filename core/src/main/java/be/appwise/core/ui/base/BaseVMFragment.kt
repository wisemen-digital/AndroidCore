package be.appwise.core.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.appwise.core.R
import be.appwise.core.extensions.fragment.snackBar
import com.orhanobut.logger.Logger

abstract class BaseVMFragment : Fragment() {
    /**
     * Reference to the viewModel that will be used for this Activity.
     * When using this class, you should override [mViewModel] by using `by viewModels()`
     *
     * ```kotlin
     *     override val mViewModel: MainViewModel by viewModels()
     * ```
     *
     * you can even add a [ViewModelFactory] to it if needed.
     *
     *```kotlin
     *     override val mViewModel: MainViewModel by viewModels() { getViewModelFactory() }
     * ```
     */
    protected abstract val mViewModel: BaseViewModel

    protected open fun getViewModelFactory(): ViewModelProvider.NewInstanceFactory =
        ViewModelProvider.NewInstanceFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.setDefaultExceptionHandler(::onError)
    }

    open fun onError(throwable: Throwable) {
        snackBar(throwable.message ?: getString(R.string.error_default))
        Logger.t("BaseVMFragment").e(throwable, throwable.message ?: getString(R.string.error_default))
    }
}