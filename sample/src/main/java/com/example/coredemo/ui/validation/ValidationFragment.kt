package com.example.coredemo.ui.validation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import be.appwise.core.ui.base.BaseBindingVMFragment
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentValidationBinding

class ValidationFragment : BaseBindingVMFragment<FragmentValidationBinding>() {

    override fun getLayout() = R.layout.fragment_validation
    override val mViewModel: ValidationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel
    }
}