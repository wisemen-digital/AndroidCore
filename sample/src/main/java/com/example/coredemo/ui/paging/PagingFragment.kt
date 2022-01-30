package com.example.coredemo.ui.paging

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import be.appwise.core.extensions.view.setupRecyclerView
import be.appwise.core.ui.base.BaseBindingVMFragment
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentPagingBinding

class PagingFragment : BaseBindingVMFragment<FragmentPagingBinding>() {

    private val pagingAdapter by lazy { PagingAdapter() }

    override fun getLayout() = R.layout.fragment_paging
    override val mViewModel: PagingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel

        //TODO: create adapter
        //TODO: create RestClient (https://open5e.com/classes ---- https://api.open5e.com/spells/)
        //TODO: create paging Repository

        initViews()
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