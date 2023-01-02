package com.depromeet.knockknock.ui.editroomdetails

interface EditRoomDetailsActionHandler {
    fun onRoomUnpublicToggled(checked: Boolean)
    fun onBackgroundClicked(backgroundUrl: String)
    fun onThumbnailClicked(thumbnailUrl: String)
    fun onBackgroundEditClicked()
    fun onThumbnailEditClicked()
    fun onSaveClicked()
}