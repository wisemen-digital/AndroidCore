package be.appwise.core.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

abstract class BaseVMActivity : BaseActivity() {
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
     *     override val mViewModel: MainViewModel by viewModels() { viewModelFactory(MainViewModel(userId)) }
     * ```
     */
    protected abstract val mViewModel: BaseViewModel

    @Deprecated("This function is actually not needed")
    protected open fun getViewModelFactory(): ViewModelProvider.NewInstanceFactory =
        ViewModelProvider.NewInstanceFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.coroutineException.observe(this) {
            if (it == null) return@observe
            onError(it)
        }
    }
}
