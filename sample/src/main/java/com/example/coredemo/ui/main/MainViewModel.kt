package com.example.coredemo.ui.main

import be.appwise.core.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    val objects = arrayListOf(
        ContentItem(1, "Measurements"),
        ContentItem(ContentItem.measurementConversion, "Conversions", "Showcasing the conversion capabilities"),
        ContentItem(ContentItem.measurementCalculation, "Calculations", "Calculating with different units"),

        ContentItem(2, "Views"),
        ContentItem(ContentItem.emptyRecyclerView, "EmptyStateRecyclerView", "Showing different states depending on data in the adapter"),

        ContentItem(3, "Networking"),
        ContentItem(ContentItem.networkingCalls, "Better response", "A new way of working with network responses"),

        ContentItem(4, "Validators"),
        ContentItem(ContentItem.validation, "Universal validation", "All the validators in a single place")
    )
}