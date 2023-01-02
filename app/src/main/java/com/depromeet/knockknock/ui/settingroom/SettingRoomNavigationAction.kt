package com.depromeet.knockknock.ui.settingroom

import com.depromeet.domain.model.Background
import com.depromeet.domain.model.Group
import com.depromeet.domain.model.Thumbnail


sealed class SettingRoomNavigationAction {
    class NavigateToCategory(
        val id: Int,
        val title: String,
        val description: String,
        val thumbnailPath: String,
        val backgroundPath: String,
        val publicAccess: Boolean,
        val categoryId: Int
    ) : SettingRoomNavigationAction()

    class NavigateToEditDetail(
        val id: Int,
        val title: String,
        val description: String,
        val thumbnailPath: String,
        val backgroundPath: String,
        val publicAccess: Boolean,
        val categoryId: Int
    ) : SettingRoomNavigationAction()

    class NavigateToLink(val roomId: Int) : SettingRoomNavigationAction()
    class NavigateToAddMember(val roomId: Int) : SettingRoomNavigationAction()
    class NavigateToRemove(val roomId: Int) : SettingRoomNavigationAction()
    class NavigateToExportMember(val userId: Int) : SettingRoomNavigationAction()
}