package be.appwise.core.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<B : ViewDataBinding>: BaseActivity() {
    protected lateinit var mBinding: B
        private set

    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = DataBindingUtil.setContentView(this, getLayout())
        mBinding.lifecycleOwner = this
        super.onCreate(savedInstanceState)
    }
}
