package com.shahar91.core.extensions.unsorted

import androidx.transition.Transition

fun Transition.onTransitionEnd(transitionEnd: () -> Unit) {
    this.addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(p0: Transition) {
            transitionEnd()
        }

        override fun onTransitionResume(p0: Transition) {

        }

        override fun onTransitionPause(p0: Transition) {

        }

        override fun onTransitionCancel(p0: Transition) {

        }

        override fun onTransitionStart(p0: Transition) {

        }
    })
}