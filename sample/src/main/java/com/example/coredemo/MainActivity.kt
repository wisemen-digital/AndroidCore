package com.example.coredemo

import android.os.Bundle
import androidx.activity.viewModels
import be.appwise.core.extensions.activity.snackBar
import be.appwise.core.ui.base.BaseBindingVMActivity
import com.example.coredemo.databinding.ActivityMainBinding

class MainActivity : BaseBindingVMActivity<ActivityMainBinding>() {

    override fun getLayout() = R.layout.activity_main
    override val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        mBinding.btnBackdrop.setOnClickListener {
            //TODO: open new Activity!!!
            snackBar("Open activity Backdrop!!")
        }
    }
}