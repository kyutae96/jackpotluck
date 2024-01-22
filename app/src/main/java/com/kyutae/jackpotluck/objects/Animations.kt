package com.kyutae.jackpotluck.objects

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import java.util.Objects

object Animations {

    fun fadeIn(view: View) {
        view.visibility = View.VISIBLE
        val fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        //FadeIn되는데 걸리는 시간 (ms)
        fadeIn.duration = 400
        fadeIn.start()
    }

    fun fadeOut(view: View) {
        val fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        //fadeOut 되는데 걸리는 시간 (ms)
        fadeOut.duration = 400
        fadeOut.start()
        view.visibility  = View.GONE
    }



}