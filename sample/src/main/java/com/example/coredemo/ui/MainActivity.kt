package com.example.coredemo.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import be.appwise.core.ui.base.BaseActivity
import com.example.coredemo.R
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : BaseActivity() {

    private lateinit var host: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        host = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment

        findViewById<MaterialToolbar>(R.id.mtbMain).setupWithNavController(host.navController)
    }
}
