package com.depromeet.knockknock.ui.setting_room

interface SettingRoomActionHandler {
    fun onCategoryClicked()
    fun onEditDetailClicked()
    fun onAddMemberClicked()
    fun onDeleteClicked()
    fun onLinkClicked()
    fun onExportClicked(userId: Int)
}