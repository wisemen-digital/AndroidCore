package be.appwise.sample_compose.util.extensions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToPrevYear() {
    this.animateScrollToPage(this.currentPage - 12)
}

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToNextYear() {
    this.animateScrollToPage(this.currentPage + 12)
}