package com.example.coredemo.ui.backdrop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.coredemo.R
import com.example.coredemo.databinding.ActivityBackdropBinding

/**
 * To create this, I followed this 'tutorial' https://codelabs.developers.google.com/codelabs/mdc-104-java
 *
 *
 * https://github.com/material-components/material-components-android/issues/90
 *
 *
 * https://medium.com/swlh/backdrop-in-android-material-design-e19fa97d4cf6
 * https://reposhub.com/android/miscellaneous/Semper-Viventem-Material-backdrop.html
 * https://github.com/kishan2612/Material-Backdrop-Android
 *
 */
class BackdropActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityBackdropBinding

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BackdropActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_backdrop)
        mBinding.lifecycleOwner = this
        super.onCreate(savedInstanceState)
    }
}