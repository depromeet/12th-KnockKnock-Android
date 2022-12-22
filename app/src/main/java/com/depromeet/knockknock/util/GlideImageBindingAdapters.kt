package com.depromeet.knockknock.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("userProfile")
fun ImageView.bindUserProfile(profileUri: String) {
    Glide.with(context)
        .load(profileUri)
        .transform(CenterCrop(), RoundedCorners(1000))
        .into(this)
}

@BindingAdapter("bookmarkContentsImage")
fun ImageView.bindBookmarkContentsImage(contentsImageUri: String) {
    Glide.with(context)
        .load(contentsImageUri)
        .transform(CenterCrop(), RoundedCorners(50))
        .into(this)
}

@BindingAdapter("roomImageImage")
fun ImageView.bindRoomImageImage(roomImgUri: String) {
    Glide.with(context)
        .load(roomImgUri)
        .transform(CenterCrop(), RoundedCorners(300))
        .into(this)
}

@BindingAdapter("roomBackgroundImage")
fun ImageView.bindroomBackgroundImage(backgroundImageUri: String) {
    Glide.with(context)
        .load(backgroundImageUri)
        .transform(CenterCrop(), RoundedCorners(20))
        .into(this)
}

@BindingAdapter("roomThumbnailImage")
fun ImageView.bindroomThumbnailImage(thumbnailImageUri: String) {
    Glide.with(context)
        .load(thumbnailImageUri)
        .transform(CenterCrop(), RoundedCorners(20))
        .into(this)
}