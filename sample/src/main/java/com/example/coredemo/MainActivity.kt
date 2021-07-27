package com.example.coredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.appwise.core.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}