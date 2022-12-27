package com.depromeet.knockknock.ui.settingroomforuser


sealed class SettingRoomForUserNavigationAction {
    class NavigateToLink(val roomId: Int): SettingRoomForUserNavigationAction()
    class NavigateToAddMember(val roomId: Int): SettingRoomForUserNavigationAction()
}