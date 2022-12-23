package com.depromeet.knockknock.ui.editprofile

sealed class SaveProfileNavigationAction {
    object NavigateToSuccess: SaveProfileNavigationAction()
    object NavigateToEditProfileImg: SaveProfileNavigationAction()
}