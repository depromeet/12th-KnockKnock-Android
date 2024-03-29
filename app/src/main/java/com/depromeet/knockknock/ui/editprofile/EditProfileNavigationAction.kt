package com.depromeet.knockknock.ui.editprofile

sealed class EditProfileNavigationAction {
    object NavigateToLogout: EditProfileNavigationAction()
    object NavigateToUserDelete: EditProfileNavigationAction()
    object NavigateToSplash: EditProfileNavigationAction()
    object NavigateToEditProfile: EditProfileNavigationAction()
}