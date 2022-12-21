package com.depromeet.knockknock.ui.register

sealed class RegisterNavigationAction {
    object NavigateToPushSetting: RegisterNavigationAction()
    object NavigateToNotificationAlarm : RegisterNavigationAction()
    object NavigateToKakaoLogin : RegisterNavigationAction()
    object NavigateToGoogleLogin : RegisterNavigationAction()
    object NavigateToLoginSuccess : RegisterNavigationAction()
}