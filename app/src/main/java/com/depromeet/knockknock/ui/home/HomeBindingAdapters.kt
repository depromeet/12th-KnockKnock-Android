package com.depromeet.knockknock.ui.home

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("homeImage")
fun ImageView.bindHomeImage(number: Int) {
    when(number) {
        1 -> {
            this.background = context.getDrawable(R.drawable.custom_yellow_radius_bottom_30) }
        2 -> {
            this.background = context.getDrawable(R.drawable.custom_purple_radius_bottom_30) }
        3 -> {
            this.background = context.getDrawable(R.drawable.custom_yellow_radius_bottom_30) }
        4 -> {
            this.background = context.getDrawable(R.drawable.custom_purple_radius_bottom_30) }
    }
}