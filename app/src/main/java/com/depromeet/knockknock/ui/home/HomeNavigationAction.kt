package com.depromeet.knockknock.ui.home

sealed class HomeNavigationAction {
    object NavigateToCreatePush: HomeNavigationAction()
    class NavigateToRoom(val roomId: Int): HomeNavigationAction()
    class NavigateToRecentAlarm(val alarmId: Int): HomeNavigationAction()
    object NavigateToNotification: HomeNavigationAction()
    class NavigateToAlarmReaction(val alarmId: Int): HomeNavigationAction()
    class NavigateToRecentAlarmMore(val alarmId: Int): HomeNavigationAction()
}