package com.depromeet.knockknock.ui.alarmcreate

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

@BindingAdapter("addImageCardViewVisible")
fun CardView.bindAddImageCardViewVisible(enable: Boolean) {
    if (enable) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

@BindingAdapter("imgLoadVisible")
fun ImageView.bindImgLoadVisible(enable: Boolean) {
    if (enable) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("deleteTextVisible")
fun TextView.bindDeleteTextVisible(textLength: Int) {
    if (textLength == 0) {
        this.setTextColor(Color.parseColor("#BDBDBD"))
        this.isClickable = false
    } else {
        this.setTextColor(Color.parseColor("#212121"))
        this.isClickable = true
    }
}

@BindingAdapter("linearLayoutColorChange")
fun LinearLayout.bindLinearLayoutColorChange(textLength: Int) {
    if (textLength == 0)  this.setBackgroundResource(R.drawable.custom_backgroundgray03_radius10)
    else this.setBackgroundResource(R.drawable.custom_backgroundgray03_radius10_line_gray08)
}

@BindingAdapter("editTextCountColorChange")
fun TextView.bindEditTextCountColorChange(textLength: Int) {
    this.text = "$textLength/200"
    if (textLength == 0) this.text = this.textChangeColor( "#ff0000", 0, 1)
    else this.text = this.textChangeColor( "#616161", 0, textLength.toString().length)
}

fun TextView.textChangeColor(
    color: String,
    start: Int,
    end: Int
): SpannableStringBuilder {
    val builder = SpannableStringBuilder(this.text.toString())

    builder.setSpan(
        ForegroundColorSpan(Color.parseColor(color)),
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return builder
}