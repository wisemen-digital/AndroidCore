package com.example.coredemo.ui.main

import be.appwise.core.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    val objects = arrayListOf(
        ContentItem(1, "Measurements"),
        ContentItem(ContentItem.measurement, "Basic", "Simple showcasing the conversions"),

        ContentItem(2, "Views"),
        ContentItem(ContentItem.emptyRecyclerView, "EmptyStateRecyclerView", "Showing different states depending on data in the adapter")
    )
}