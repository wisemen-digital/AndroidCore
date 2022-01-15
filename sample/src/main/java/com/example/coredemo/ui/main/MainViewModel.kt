package com.example.coredemo.ui.main

import be.appwise.core.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    val objects = arrayListOf(
        ContentItem(1, "Title"),
        ContentItem(10, "Content Name", "Content Description"),
        )
}