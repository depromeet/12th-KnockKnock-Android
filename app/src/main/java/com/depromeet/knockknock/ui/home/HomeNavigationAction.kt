package com.depromeet.knockknock.ui.home

sealed class HomeNavigationAction {
    object NavigateToCreatePush: HomeNavigationAction()
    class NavigateToRoom(roomId: Int): HomeNavigationAction()
    class NavigateToRecentAlarm(alarmId: Int): HomeNavigationAction()
    object NavigateToNotification: HomeNavigationAction()
    class NavigateToAlarmReaction(alarmId: Int): HomeNavigationAction()
}