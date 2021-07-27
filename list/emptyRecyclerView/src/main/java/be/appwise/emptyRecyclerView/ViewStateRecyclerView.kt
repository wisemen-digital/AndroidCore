package be.appwise.emptyRecyclerView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * ## Use as following: ##
 *
 * ```
 *     recyclerView?.apply {
 *       layoutManager = GridLayoutManager(context, 2)
 *       emptyStateView = emptyView
 *       loadingStateView = loadingView
 *       adapter = adapterGrid
 *     }
 *
 *     // you can set LoadingView or emptyView manual
 *     recyclerView.stateView = RecyclerViewEnum.EMPTY_STATE
 *     recyclerView.stateView = RecyclerViewEnum.LOADING
 * ```
 *
 *
 * ## An XML example: ##
 *
 * ```
 *     <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
 *         android:layout_width="match_parent"
 *         android:layout_height="match_parent"
 *         android:gravity="center"
 *         android:orientation="vertical">
 *
 *         <com.google.android.material.progressindicator.ProgressIndicator
 *             android:id="@+id/loadingView"
 *             android:layout_width="wrap_content"
 *             android:layout_height="wrap_content" />
 *
 *         <TextView
 *             android:id="@+id/emptyView"
 *             android:layout_width="wrap_content"
 *             android:layout_height="wrap_content" />
 *
 *         <be.appwise.emptyRecyclerView.ViewStateRecyclerView
 *             android:id="@+id/rvCategories"
 *             android:layout_width="match_parent"
 *             android:layout_height="match_parent" />
 *     </LinearLayout>
 * ```
 * See [this SO post](https://stackoverflow.com/a/52716769/2263408) for more info
 */
class ViewStateRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var state: RecyclerViewState? = RecyclerViewState.LOADING
        set(value) {
            field = value
            updateState()
        }
    var emptyStateView: View? = null
    var loadingStateView: View? = null

    private val dataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            onChangeState()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            onChangeState()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            onChangeState()
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(dataObserver)
        dataObserver.onChanged()
    }

    fun onChangeState() {
        state = if (adapter?.itemCount == 0) {
            RecyclerViewState.EMPTY
        } else {
            RecyclerViewState.NORMAL
        }
    }

    private fun updateState() {
        when (state) {
            RecyclerViewState.LOADING -> {
                loadingStateView?.visibility = View.VISIBLE
                this@ViewStateRecyclerView.visibility = View.GONE
                emptyStateView?.visibility = View.GONE
            }

            RecyclerViewState.NORMAL -> {
                loadingStateView?.visibility = View.GONE
                this@ViewStateRecyclerView.visibility = View.VISIBLE
                emptyStateView?.visibility = View.GONE
            }
            RecyclerViewState.EMPTY -> {
                loadingStateView?.visibility = View.GONE
                this@ViewStateRecyclerView.visibility = View.GONE
                emptyStateView?.visibility = View.VISIBLE
            }
        }
    }

    fun resetState() {
        onChangeState()
    }

    //<editor-fold desc="Needed for Fading edge with Padding">

    // This resolves an issue with a RecyclerView which has both a 'Fading Edge' as well as a 'Padding (Bot or Top)'
    // See https://stackoverflow.com/a/56338638/2263408 for more information
    override fun isPaddingOffsetRequired(): Boolean {
        return true
    }

    override fun getTopPaddingOffset(): Int {
        return -paddingTop
    }

    override fun getBottomPaddingOffset(): Int {
        return paddingBottom
    }
    //</editor-fold>
}