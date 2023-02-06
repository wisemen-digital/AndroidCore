package com.example.coredemo.ui.validation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import be.appwise.core.ui.base.BaseBindingVMFragment
import be.appwise.core.validation.rules.edittext.EmailRule
import be.appwise.core.validation.rules.edittext.NotEmptyOrBlankRule
import be.appwise.core.validation.validators.EditTextViewValidator
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentValidationBinding

class ValidationFragment : BaseBindingVMFragment<FragmentValidationBinding>() {

    override fun getLayout() = R.layout.fragment_validation
    override val mViewModel: ValidationViewModel by viewModels()

    private fun initCompositeValidation() {
        mViewModel.validator.add(
            EditTextViewValidator(mBinding.etEmail) {
                add(NotEmptyOrBlankRule())
                add(EmailRule())
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel

        initCompositeValidation()
        mViewModel.validator.validResultLive.observe(viewLifecycleOwner) {
            Log.d("ValidationFragment", "onViewCreated: $it")
        }
    }
}