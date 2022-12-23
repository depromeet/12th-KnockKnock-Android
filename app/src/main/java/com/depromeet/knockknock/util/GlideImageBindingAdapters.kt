package com.depromeet.knockknock.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("userProfile")
fun ImageView.bindUserProfile(profileUri: String?) {
    profileUri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(1000))
            .into(this)
    }
}

@BindingAdapter("bookmarkContentsImage")
fun ImageView.bindBookmarkContentsImage(contentsImageUri: String?) {
    contentsImageUri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(50))
            .into(this)
    }
}

@BindingAdapter("roomImageImage")
fun ImageView.bindRoomImageImage(roomImgUri: String?) {
    roomImgUri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(300))
            .into(this)
    }
}

@BindingAdapter("glide10000")
fun ImageView.bindGlide10000(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(10000))
            .into(this)
    }
}

@BindingAdapter("glide1000")
fun ImageView.bindGlide1000(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(1000))
            .into(this)
    }
}

@BindingAdapter("glide300")
fun ImageView.bindGlide300(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(300))
            .into(this)
    }
}

@BindingAdapter("glide100")
fun ImageView.bindGlide100(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(100))
            .into(this)
    }
}

@BindingAdapter("glide50")
fun ImageView.bindGlide50(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(50))
            .into(this)
    }
}