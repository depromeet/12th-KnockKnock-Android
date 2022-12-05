package com.depromeet.knockknock.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import com.depromeet.knockknock.R

fun EditText.customOnFocusChangeListener(context: Context) = View.OnFocusChangeListener { view, gainFocus ->
    //포커스가 주어졌을 때
    Log.d("response!!!", gainFocus.toString())
    if (gainFocus) view.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius16_line_gray06)
    else view.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius16)
}
