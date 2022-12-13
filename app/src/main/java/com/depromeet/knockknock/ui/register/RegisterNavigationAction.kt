package com.depromeet.knockknock.ui.register

sealed class RegisterNavigationAction {
    object NavigateToPushSetting: RegisterNavigationAction()
    object NavigateToNotificationAlarm : RegisterNavigationAction()
}