package be.appwise.core.ui.base

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingVMActivity<VM : BaseViewModel, B : ViewDataBinding> : BaseVMActivity<VM>() {
    protected lateinit var mBinding: B
        private set

    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun init() {
        mBinding = DataBindingUtil.setContentView(this, getLayout())
        super.init()
    }
}
