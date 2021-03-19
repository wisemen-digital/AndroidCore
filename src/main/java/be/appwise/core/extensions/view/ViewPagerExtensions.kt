package be.appwise.core.extensions.view

import androidx.viewpager.widget.ViewPager

/**
 * Now you can choose which callback you use, instead of being forced to implement all 3 of them.
 * For this to work just use the named parameters in the function.
 *
 * @param onPageScrollStateChanged A function that will be called when the state has changed
 * @param onPageScrolled A function that will be called when a page is scrolling
 * @param pageChange A function that will be called when a page has been changed
 */
fun ViewPager.onPageChange(onPageScrollStateChanged: (Int) -> Unit = {}, onPageScrolled: (Int, Float, Int) -> Unit = { _, _, _ -> }, pageChange: (Int) -> Unit = {}) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            pageChange(position)
        }
    })
}