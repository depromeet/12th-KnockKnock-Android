package com.depromeet.knockknock.ui.alarmcreate

sealed class AlarmCreateNavigationAction {
    object NavigateToAlarmSend : AlarmCreateNavigationAction()
    object NavigateToAddImage: AlarmCreateNavigationAction()
    object NavigateToFocusTitleText: AlarmCreateNavigationAction()
    object NavigateToDeleteMessageText: AlarmCreateNavigationAction()
}