package com.depromeet.knockknock.ui.editroomdetails

interface EditRoomDetailsActionHandler {
    fun onRoomUnpublicToggled(checked: Boolean)
    fun onBackgroundClicked(backgroundId: Int)
    fun onThumbnailClicked(thumbnailId: Int)
    fun onBackgroundEditClicked()
    fun onThumbnailEditClicked()
    fun onSaveClicked()
}