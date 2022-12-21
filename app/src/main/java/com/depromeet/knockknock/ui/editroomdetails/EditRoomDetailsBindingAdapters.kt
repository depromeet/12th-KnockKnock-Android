package com.depromeet.knockknock.ui.editroomdetails

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("saveBtnEnable")
fun TextView.bindSaveBtnEnable(enable: Boolean) {
    if(enable) {
        this.setTextColor(context.getColor(R.color.black))
        this.background = context.getDrawable(R.drawable.custom_yellow_radius10)
    } else {
        this.setTextColor(context.getColor(R.color.gray08))
        this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius10)
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("backgroundClicked")
fun ConstraintLayout.bindBackgroundClicked(checked: Boolean) {
    if(checked) {
        this.background = context.getDrawable(R.drawable.custom_yellow_radius16)
    } else {
        this.background = context.getDrawable(R.drawable.custom_transparents_radius16_line_gray04)
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("thumbnailClicked")
fun ConstraintLayout.bindThumbnailClicked(checked: Boolean) {
    if(checked) {
        this.background = context.getDrawable(R.drawable.custom_yellow_radius16)
    } else {
        this.background = context.getDrawable(R.drawable.custom_transparents_radius16_line_gray04)
    }
}