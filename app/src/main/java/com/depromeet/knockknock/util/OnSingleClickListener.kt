package com.depromeet.knockknock.util

import android.view.View
import android.widget.CompoundButton
import java.util.concurrent.atomic.AtomicBoolean

class OnSingleClickListener(
    private val clickListener: View.OnClickListener,
    private val intervalMs: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                clickListener.onClick(v)
            }
        }
    }
}

class OnSingleCheckedChangeListener(
    private val changeListener: CompoundButton.OnCheckedChangeListener,
    private val intervalMs: Long = 300
) : CompoundButton.OnCheckedChangeListener {
    private var canClick = AtomicBoolean(true)

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (canClick.getAndSet(false)) {
            buttonView?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                changeListener.onCheckedChanged(buttonView, isChecked)
            }
        }
    }
}