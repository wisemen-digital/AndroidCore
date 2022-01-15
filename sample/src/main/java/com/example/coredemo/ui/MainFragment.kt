package com.example.coredemo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import be.appwise.core.ui.base.BaseBindingVMFragment
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentMainBinding

class MainFragment : BaseBindingVMFragment<FragmentMainBinding>() {

    override fun getLayout() = R.layout.fragment_main
    override val mViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel
    }
}