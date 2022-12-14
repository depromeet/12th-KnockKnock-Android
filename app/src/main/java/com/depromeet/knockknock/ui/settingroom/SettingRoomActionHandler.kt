package com.depromeet.knockknock.ui.settingroom

interface SettingRoomActionHandler {
    fun onCategoryClicked()
    fun onEditDetailClicked()
    fun onAddMemberClicked()
    fun onDeleteClicked()
    fun onLinkClicked()
    fun onExportClicked(userId: Int)
}