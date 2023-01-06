package com.depromeet.knockknock.ui.bookmark

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

@BindingAdapter("resetVisible")
fun ConstraintLayout.bindResetVisible(filterChecked: Boolean) {
    if(filterChecked) this.visibility = View.VISIBLE
    else  this.visibility = View.GONE
}

@SuppressLint("SetTextI18n")
@BindingAdapter("roomCountChecked")
fun TextView.bindRoomCountChecked(roomChecked: Int) {
    if(roomChecked == 0 ) {
        this.text = context.getText(R.string.room)
    } else {
        this.text = roomChecked.toString()+"개 선택"
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("roomFilterBackground")
fun ConstraintLayout.bindRoomFilterBackground(roomChecked: Int) {
    if(roomChecked == 0 ) this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius80)
    else this.background = context.getDrawable(R.drawable.custom_yellow_radius80)
}

@BindingAdapter("periodCountChecked")
fun TextView.bindPeriodCountChecked(periodChecked: Int) {
    when(periodChecked) {
        0 -> this.text = context.getString(R.string.all_period)
        1 -> this.text = context.getString(R.string.one_month)
        3 -> this.text = context.getString(R.string.three_month)
        6 -> this.text = context.getString(R.string.six_month)
        12 -> this.text = context.getString(R.string.one_year)
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("periodFilterBackground")
fun ConstraintLayout.bindPeriodFilterBackground(periodChecked: Int) {
    if(periodChecked == 0 ) this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius80)
    else this.background = context.getDrawable(R.drawable.custom_yellow_radius80)
}

