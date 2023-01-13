package com.depromeet.knockknock.ui.alarmcreate

sealed class AlarmCreateNavigationAction {
    object NavigateToBackStack : AlarmCreateNavigationAction()
    object NavigateToAlarmSend : AlarmCreateNavigationAction()
    object NavigateToAddImage : AlarmCreateNavigationAction()
    object NavigateToFocusTitleText : AlarmCreateNavigationAction()
    object NavigateToDeleteMessageText : AlarmCreateNavigationAction()
    class NavigateToRecommendationMessageText(val message: String) : AlarmCreateNavigationAction()
    class NavigateToPreview(val title: String, val message: String, val uri: String) :
        AlarmCreateNavigationAction()
    object NavigateToPushAlarm : AlarmCreateNavigationAction()
    object NavigateToNoReservationAlarm : AlarmCreateNavigationAction()


}