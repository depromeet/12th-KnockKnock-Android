package com.depromeet.knockknock.ui.alarmcreate

sealed class AlarmCreateNavigationAction {
    object NavigateToGallery : AlarmCreateNavigationAction()
    object NavigateToAlarmSend : AlarmCreateNavigationAction()

}