package com.example.coredemo.ui.paging

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import be.appwise.core.extensions.view.setupRecyclerView
import be.appwise.core.ui.base.BaseBindingVMFragment
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentPagingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class PagingFragment : BaseBindingVMFragment<FragmentPagingBinding>() {

    private val pagingAdapter by lazy { PagingAdapter() }

    override fun getLayout() = R.layout.fragment_paging
    override val mViewModel: PagingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel

        initViews()

        mViewModel.spellsLive.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenResumed {
                pagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            // Get the load state of the paged adapter to update a view in the UI
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                mViewModel.isLoading(loadStates.refresh is LoadState.Loading || loadStates.append is LoadState.Loading || loadStates.prepend is LoadState.Loading)
            }
        }

    }

    private fun initViews() {
        mBinding.let {
            it.rvPaging.let { rv ->
                rv.setupRecyclerView(null)
                rv.adapter = pagingAdapter
            }
        }
    }
}