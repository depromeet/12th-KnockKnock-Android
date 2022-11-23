package com.depromeet.knockknock.ui.mypage

sealed class MypageNavigationAction {
    object NavigateToProfileEdit: MypageNavigationAction()
    object NavigateToAlarmSetting: MypageNavigationAction()
    object NavigateToBookmark: MypageNavigationAction()
    object NavigateToFriendList: MypageNavigationAction()
    object NavigateToInformation: MypageNavigationAction()
}