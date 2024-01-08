package be.appwise.core.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding

abstract class BaseBindingVMDialogFragment<B : ViewDataBinding>: BaseDialogFragment() {
    protected abstract val mViewModel: BaseViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.coroutineException.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            onError(it)
        }
    }
}
