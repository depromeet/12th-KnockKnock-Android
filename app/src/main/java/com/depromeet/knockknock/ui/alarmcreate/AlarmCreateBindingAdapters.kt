package com.depromeet.knockknock.ui.alarmcreate

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter

@BindingAdapter("addImageCardViewVisible")
fun CardView.bindAddImageCardViewVisible(enable: Boolean) {
    Log.d("ttt", enable.toString())
    if (enable) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

@BindingAdapter("imgLoadVisible")
fun ImageView.bindImgLoadVisible(enable: Boolean) {
    if (enable) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun textChangeColor(
    text: TextView,
    color: String,
    start: Int,
    end: Int
): SpannableStringBuilder {
    val builder = SpannableStringBuilder(text.text.toString())

    builder.setSpan(
        ForegroundColorSpan(Color.parseColor(color)),
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return builder
}