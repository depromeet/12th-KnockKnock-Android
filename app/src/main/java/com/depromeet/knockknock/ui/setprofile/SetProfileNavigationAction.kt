package com.depromeet.knockknock.ui.setprofile


sealed class SetProfileNavigationAction {
    object NavigateToSetProfileImage: SetProfileNavigationAction()
    object NavigateToHome: SetProfileNavigationAction()
    object NavigateToEmpty: SetProfileNavigationAction()
}