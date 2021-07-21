package com.example.coredemo.ui.backdrop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.coredemo.R
import com.example.coredemo.databinding.ActivityBackdropBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * To create this, I followed this 'tutorial' https://codelabs.developers.google.com/codelabs/mdc-104-java
 *
 *
 * https://github.com/material-components/material-components-android/issues/90
 *
 *
 * https://medium.com/swlh/backdrop-in-android-material-design-e19fa97d4cf6
 * https://reposhub.com/android/miscellaneous/Semper-Viventem-Material-backdrop.html
 * https://github.com/kishan2612/Material-Backdrop-Android
 *
 *
 * Seems promising... I think... --> Nice start, but when the backLayer grows larger than the screen, it pushes the frontLayer offscreen...
 * https://github.com/Semper-Viventem/Material-backdrop
 * https://stackoverflow.com/questions/52541557/using-backdrop-on-android/53800236
 *
 *
 *
 * Something to try out?
 * https://github.com/milindrc/BackdropEasy
 */
class BackdropActivity : AppCompatActivity() {

    enum class DropState {
        OPEN,
        CLOSE
    }

    private lateinit var mBinding: ActivityBackdropBinding
    private var mState = DropState.OPEN
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BackdropActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_backdrop)
        mBinding.lifecycleOwner = this
        super.onCreate(savedInstanceState)

        bottomSheetBehavior = BottomSheetBehavior.from(mBinding.frontLayout)

        drawDropState()
        mBinding.mtbToolbar.setNavigationOnClickListener {
            mState = when (mState) {
                DropState.OPEN -> DropState.CLOSE
                DropState.CLOSE -> DropState.OPEN
            }
            drawDropState()
        }
    }

    private fun drawDropState() {
        when (mState) {
            DropState.CLOSE -> {
                mBinding.mtbToolbar.setNavigationIcon(R.drawable.ic_close)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            DropState.OPEN -> {
                mBinding.mtbToolbar.setNavigationIcon(R.drawable.ic_menu)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }
}