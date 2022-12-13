package com.depromeet.knockknock.ui.setprofile

import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction

sealed class SetProfileNavigationAction {
    object NavigateToSetProfileImage: SetProfileNavigationAction()
    object NavigateToHome: SetProfileNavigationAction()
}