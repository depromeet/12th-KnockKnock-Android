package com.depromeet.knockknock.ui.alarmroomsearch

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R
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


@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("roomCategoryIsNull")
fun ConstraintLayout.bindroomCategoryIsNull(categoryLength: Int) {
    if(categoryLength==0) {
        this.background = context.getDrawable(custom_transparents_radius08_line_transparent)
    } else {
        this.background = context.getDrawable(custom_yellow_radius16)
    }
}


