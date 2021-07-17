package com.example.coredemo.ui

import android.os.Bundle
import androidx.activity.viewModels
import be.appwise.core.ui.base.BaseBindingVMActivity
import com.example.coredemo.R
import com.example.coredemo.databinding.ActivityMainBinding
import com.example.coredemo.ui.backdrop.BackdropActivity

class MainActivity : BaseBindingVMActivity<ActivityMainBinding>() {

    override fun getLayout() = R.layout.activity_main
    override val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        mBinding.btnBackdrop.setOnClickListener {
            startActivity(BackdropActivity.newIntent(this))
        }
    }
}