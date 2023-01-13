package com.depromeet.knockknock.ui.splash


sealed class SplashNavigationAction {
    object NavigateToAlreadyLogin: SplashNavigationAction()
    object NavigateToFirstLogin: SplashNavigationAction()
}