package com.depromeet.knockknock.ui.alarmroomexplore

import android.annotation.SuppressLint
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R.drawable.*

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("roomUnpublicIconEnable")
fun ConstraintLayout.bindroomUnpublicIconEnable(enable: Boolean) {
    if(enable) {
        this.background = context.getDrawable(ic_secret)
    } else {
        this.background = context.getDrawable(custom_transparents_radius08_line_transparent)
    }
}


