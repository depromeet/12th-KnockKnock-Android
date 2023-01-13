package com.depromeet.knockknock.ui.category

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
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
@BindingAdapter("categoryClicked")
fun ConstraintLayout.bindCategoryClicked(checked: Boolean) {
    if(checked) {
        this.background = context.getDrawable(R.drawable.custom_yellow_radius16)
    } else {
        this.background = context.getDrawable(R.drawable.custom_transparents_radius16_line_gray04)
    }
}