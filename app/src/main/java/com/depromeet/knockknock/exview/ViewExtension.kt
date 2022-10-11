package com.dida.android.presentation.views.nav.home

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.widget.NestedScrollView
import kotlin.math.abs

private fun NestedScrollView.computeDistanceToView(view: View): Int {
    return abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
}

private fun calculateRectOnScreen(view: View): Rect {
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    return Rect(
        location[0],
        location[1],
        location[0] + view.measuredWidth,
        location[1] + view.measuredHeight
    )
}

internal fun NestedScrollView.smoothScrollToView(
    view: View,
    marginTop: Int = 0,
    maxDuration: Long = 50L,
    onEnd: () -> Unit = {}
) {
    if (this.getChildAt(0).height <= this.height) { // 스크롤의 의미가 없다.
        onEnd()
        return
    }
    val y = computeDistanceToView(view) - marginTop
    val ratio = abs(y - this.scrollY) / (this.getChildAt(0).height - this.height).toFloat()
    ObjectAnimator.ofInt(this, "scrollY", y).apply {
        duration = (maxDuration * ratio).toLong()
        interpolator = AccelerateDecelerateInterpolator()
        doOnEnd {
            onEnd()
        }
        start()
    }
}