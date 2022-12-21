package com.depromeet.knockknock.ui.editroomdetails

interface EditRoomDetailsActionHandler {
    fun onRoomPublicToggled(checked: Boolean)
    fun onBackgroundClicked(backgroundId: Int)
    fun onThumbnailClicked(thumbnailId: Int)
    fun onSaveClicked()
}