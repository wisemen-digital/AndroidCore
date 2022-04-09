package com.example.coredemo.ui.views.stateRecyclerView

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import be.appwise.core.ui.base.BaseBindingVMFragment
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentStateRecyclerViewBinding

class StateRecyclerViewFragment : BaseBindingVMFragment<FragmentStateRecyclerViewBinding>() {

    override fun getLayout() = R.layout.fragment_state_recycler_view
    override val mViewModel: StateRecyclerViewViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel
    }
}