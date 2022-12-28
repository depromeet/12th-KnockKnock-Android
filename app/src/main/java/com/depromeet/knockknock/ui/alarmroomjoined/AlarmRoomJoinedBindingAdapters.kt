package com.depromeet.knockknock.ui.alarmroomjoined

import android.annotation.SuppressLint
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R
import com.depromeet.knockknock.R.drawable.*
import com.depromeet.knockknock.ui.bookmark.bindRoomFilterBackground

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("roomUnpublicIconEnable")
fun ConstraintLayout.bindroomUnpublicIconEnable(enable: Boolean) {
    if(enable) {
        this.background = context.getDrawable(ic_secret)
    } else {
        this.background = context.getDrawable(custom_transparents_radius08_line_transparent)
    }
}


@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("roomTypeClicked")
fun ConstraintLayout.bindRoomTypeClicked(checked: Boolean) {
    if(checked) {
        this.background = context.getDrawable(R.drawable.custom_yellow_radius80)
    } else {
        this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius80)
    }
}


