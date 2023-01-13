package com.depromeet.knockknock.ui.aloneroomdetails

interface AloneRoomDetailsActionHandler {
    fun onRoomUnpublicToggled(checked: Boolean)
    fun onBackgroundClicked(backgroundUrl: String)
    fun onThumbnailClicked(thumbnailUrl: String)
    fun onBackgroundEditClicked()
    fun onThumbnailEditClicked()
    fun onNextClicked()
}