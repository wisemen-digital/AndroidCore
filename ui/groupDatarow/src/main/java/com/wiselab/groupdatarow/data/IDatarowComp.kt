package com.wiselab.groupdatarow.data

import androidx.compose.runtime.Composable

interface IDatarowComp : IDatarow {
    override val title: String
    override val value: String?
    val iconComp: (@Composable () -> Unit)?
    override val onClick: (() -> Unit)?
}