package com.example.coredemo.ui.main

data class ContentItem(val id: Int, val name: String, val desc: String) {
    var isSection = false
        private set

    constructor(id: Int, name: String) : this(id, name, "") {
        isSection = true
    }

    companion object {
        const val measurement = 10
        const val emptyRecyclerView = 20
    }
}