package com.example.coredemo.ui.views.stateRecyclerView

import be.appwise.core.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StateRecyclerViewViewModel : BaseViewModel() {

    private val _itemList: MutableStateFlow<List<StateRecyclerViewItem>> = MutableStateFlow(listOf())
    val itemList: StateFlow<List<StateRecyclerViewItem>> get() = _itemList

    init {
        vmScope.launch {
            delay(5000)
            _itemList.value = itemList.value.plus(
                mutableListOf(
                    StateRecyclerViewItem(1, "hello"),
                    StateRecyclerViewItem(2, "What"),
                    StateRecyclerViewItem(3, "is"),
                    StateRecyclerViewItem(4, "this"),
                    StateRecyclerViewItem(5, "right now")
                )
            )
        }
    }

    fun addItem() {
        _itemList.value = itemList.value.plus(
            StateRecyclerViewItem(3, "is")
        )
    }
}