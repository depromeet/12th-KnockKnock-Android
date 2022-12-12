package com.depromeet.knockknock.ui.editbookmark

import android.annotation.SuppressLint
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("editBtnEnable")
fun TextView.bindEditBtnEnable(enable: Boolean) {
    if(enable) this.setTextColor(context.getColor(R.color.gray07))
    else this.setTextColor(context.getColor(R.color.main_yellow_light_mode))
}

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("deleteBtnEnable")
fun TextView.bindDeleteBtnEnable(enable: Boolean) {
    if(enable) {
        this.setTextColor(context.getColor(R.color.black))
        this.background = context.getDrawable(R.drawable.custom_yellow_radius10)
    } else {
        this.setTextColor(context.getColor(R.color.gray07))
        this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius10)
    }
}