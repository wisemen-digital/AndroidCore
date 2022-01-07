package be.appwise.core.ui.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider

abstract class BaseVMFragment : BaseFragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.coroutineException.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            onError(it)
        }
    }
}