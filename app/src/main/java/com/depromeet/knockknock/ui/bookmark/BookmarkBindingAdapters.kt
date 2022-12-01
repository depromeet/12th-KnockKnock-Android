package com.depromeet.knockknock.ui.bookmark

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R
import com.depromeet.knockknock.ui.bookmark.model.FilterType

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("filterAllBackground")
fun View.bindFilterAllBackground(type: FilterType) {
    if(type == FilterType.ALL) this.background = this.context.getDrawable(R.drawable.custom_yellow_radius12)
    else this.background = this.context.getDrawable(R.drawable.custom_backgroundwhite_radius12)
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("filterAllTextColor")
fun TextView.bindFilterAllTextColor(type: FilterType) {
    if(type == FilterType.ALL) this.setTextColor(getColor(this.context, R.color.black))
    else this.setTextColor(getColor(this.context, R.color.gray08))
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("filterRoomBackground")
fun View.bindFilterRoomBackground(type: FilterType) {
    if(type == FilterType.ROOM) this.background = this.context.getDrawable(R.drawable.custom_yellow_radius12)
    else this.background = this.context.getDrawable(R.drawable.custom_backgroundwhite_radius12)
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("filterRoomTextColor")
fun TextView.bindFilterRoomTextColor(type: FilterType) {
    if(type == FilterType.ROOM) this.setTextColor(getColor(this.context, R.color.black))
    else this.setTextColor(getColor(this.context, R.color.gray08))
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("filterPeriodBackground")
fun View.bindFilterPeriodBackground(type: FilterType) {
    if(type == FilterType.PERIOD) this.background = this.context.getDrawable(R.drawable.custom_yellow_radius12)
    else this.background = this.context.getDrawable(R.drawable.custom_backgroundwhite_radius12)
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("filterPeriodTextColor")
fun TextView.bindFilterPeriodTextColor(type: FilterType) {
    if(type == FilterType.PERIOD) this.setTextColor(getColor(this.context, R.color.black))
    else this.setTextColor(getColor(this.context, R.color.gray08))
}