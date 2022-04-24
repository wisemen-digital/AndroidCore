package com.example.coredemo.ui.views.stateRecyclerView

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import be.appwise.core.extensions.view.setupRecyclerView
import be.appwise.core.ui.base.BaseBindingVMFragment
import be.appwise.emptyRecyclerView.RecyclerViewState
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentStateRecyclerViewBinding
import com.example.coredemo.ui.views.stateRecyclerView.adapter.StateRecyclerViewAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class StateRecyclerViewFragment : BaseBindingVMFragment<FragmentStateRecyclerViewBinding>() {

    override fun getLayout() = R.layout.fragment_state_recycler_view
    override val mViewModel: StateRecyclerViewViewModel by viewModels()

    private val stateRecyclerViewAdapter by lazy { StateRecyclerViewAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel

        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            // "drop(1)" makes sure that the default value (which returns immediately) will not mess up the loading indicator
            mViewModel.itemList.drop(1).collectLatest {
                stateRecyclerViewAdapter.submitList(it)
            }
        }
    }

    private fun initViews() {
        mBinding.rvItems.setupRecyclerView(null)
        mBinding.rvItems.adapter = stateRecyclerViewAdapter
        mBinding.rvItems.loadingStateView = mBinding.cpiLoading
        mBinding.rvItems.emptyStateView = mBinding.tvEmpty
        mBinding.rvItems.state = RecyclerViewState.LOADING

        mBinding.btnAdd.setOnClickListener {
            mViewModel.addItem()
        }

        mBinding.btnLoading.setOnClickListener {
            mBinding.rvItems.state = RecyclerViewState.LOADING
            lifecycleScope.launch {
                delay(1000)
                mBinding.rvItems.resetState()
            }
        }
    }
}