package com.example.coredemo.ui.paging

import be.appwise.core.ui.base.BaseViewModel
import com.example.coredemo.data.repositories.SpellRepo

class PagingViewModel : BaseViewModel() {

    init {
        launchAndLoad {
            SpellRepo.getSpells()
        }
    }
}