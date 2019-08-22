package be.appwise.core.extensions.layout

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

inline fun ConstraintLayout.updateParams(
    constraintSet: ConstraintSet = ConstraintSet(),
    updates: ConstraintSet.() -> Unit
) {
    constraintSet.clone(this)
    constraintSet.updates()
    constraintSet.applyTo(this)
}