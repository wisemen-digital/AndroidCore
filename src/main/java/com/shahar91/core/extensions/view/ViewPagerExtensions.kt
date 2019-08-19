package com.shahar91.core.extensions.view

import androidx.viewpager.widget.ViewPager

fun ViewPager.onPageChange(pageChange: (Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            pageChange(position)
        }
    })
}