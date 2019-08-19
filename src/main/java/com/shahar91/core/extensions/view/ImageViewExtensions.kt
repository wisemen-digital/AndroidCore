package com.shahar91.core.extensions.view

import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.widget.ImageView

/**
 * Animates between 2 drawables
 * @param drawableStart drawable to start with
 * @param drawableEnd drawable to end with
 * @param duration length of the transition
 *
 * @see TransitionDrawable
 */
fun ImageView.animateBetweenDrawables(drawableStart: Drawable, drawableEnd: Drawable, duration: Int = 350) {
    val transitionDrawable = TransitionDrawable(
        arrayOf(drawableStart, drawableEnd)
    )
    setImageDrawable(transitionDrawable)
    transitionDrawable.startTransition(duration)
}

/**
 * Animates between 2 drawables
 * @param drawable drawable to transition to
 * @param duration length of the transition
 *
 * @see ImageView.animateBetweenDrawables
 */
fun ImageView.animateToDrawable(drawable: Drawable, duration: Int = 350) =
    animateBetweenDrawables(this.drawable, drawable, duration)