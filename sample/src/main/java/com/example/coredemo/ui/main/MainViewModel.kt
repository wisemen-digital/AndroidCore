package com.example.coredemo.ui.main

import be.appwise.core.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    val objects = arrayListOf(
        ContentItem(1, "Measurements"),
        ContentItem(ContentItem.measurement, "Basic", "Simple showcasing the conversions"),

        ContentItem(2, "Views"),
        ContentItem(ContentItem.emptyRecyclerView, "EmptyStateRecyclerView", "Showing different states depending on data in the adapter"),

        ContentItem(3, "Paging"),
        ContentItem(ContentItem.paging, "Paging With Filter/Query", "Using the paging library to showcase the BaseRemoteMediator and more")
    )
}