package com.example.coredemo.ui.paging

import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import be.appwise.core.ui.base.BaseViewModel
import com.example.coredemo.data.repositories.SpellRepo

class PagingViewModel : BaseViewModel() {

    @ExperimentalPagingApi
    val spellsLive get() = SpellRepo.pagingDataAsFlow.asLiveData().cachedIn(this)
}