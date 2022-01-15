package com.example.coredemo.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import be.appwise.core.extensions.fragment.snackBar
import be.appwise.core.extensions.view.setupRecyclerView
import be.appwise.core.ui.base.BaseBindingVMFragment
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : BaseBindingVMFragment<FragmentMainBinding>() {

    override fun getLayout() = R.layout.fragment_main
    override val mViewModel: MainViewModel by viewModels()

    private val contentItemAdapter by lazy {
        ContentItemAdapter {
            //TODO: do something with the result... probably navigate to a new fragment to showcase something
            Snackbar.make(requireView(), it.name, Snackbar.LENGTH_SHORT).show()
        }
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