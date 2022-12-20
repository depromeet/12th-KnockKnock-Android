package com.depromeet.knockknock.ui.settingroom


sealed class SettingRoomNavigationAction {
    object NavigateToCategory: SettingRoomNavigationAction()
    class NavigateToEditDetail(val roomId: Int): SettingRoomNavigationAction()
    class NavigateToLink(val roomId: Int): SettingRoomNavigationAction()
    class NavigateToAddMember(val roomId: Int): SettingRoomNavigationAction()
    class NavigateToRemove(val roomId: Int): SettingRoomNavigationAction()
    class NavigateToExportMember(val userId: Int): SettingRoomNavigationAction()
}