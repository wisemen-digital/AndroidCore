package com.example.coredemo.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import be.appwise.core.extensions.view.setupRecyclerView
import be.appwise.core.ui.base.BaseBindingVMFragment
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentMainBinding

class MainFragment : BaseBindingVMFragment<FragmentMainBinding>() {

    override fun getLayout() = R.layout.fragment_main
    override val mViewModel: MainViewModel by viewModels()

    private val contentItemAdapter by lazy {
        ContentItemAdapter {
            handleNavigationToContent(it)
        }
    }

    private fun handleNavigationToContent(it: ContentItem) {
        when (it.id) {
            ContentItem.measurement -> MainFragmentDirections.actionMainFragmentToMeasurementsFragment()
//            ContentItem.emptyRecyclerView -> {}
            ContentItem.paging -> MainFragmentDirections.actionMainFragmentToPagingFragment()
            else -> null
        }?.run(findNavController()::navigate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel

        initViews()

        contentItemAdapter.submitList(mViewModel.objects)
    }

    private fun initViews() {
        mBinding.rvContentItems.let {
            it.setupRecyclerView()
            it.adapter = contentItemAdapter
        }
    }
}