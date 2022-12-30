package com.depromeet.knockknock.ui.setprofile

import com.depromeet.domain.model.Profile


sealed class SetProfileNavigationAction {
    class NavigateToSetProfileImage(val profile: Profile): SetProfileNavigationAction()
    object NavigateToHome: SetProfileNavigationAction()
    object NavigateToEmpty: SetProfileNavigationAction()
}