package com.wiselab.groupdatarow.data

import androidx.compose.ui.graphics.vector.ImageVector

interface IDatarow {
    val title: String
    val value: String?
    val icon: ImageVector?
    val onClick: (() -> Unit)?
}