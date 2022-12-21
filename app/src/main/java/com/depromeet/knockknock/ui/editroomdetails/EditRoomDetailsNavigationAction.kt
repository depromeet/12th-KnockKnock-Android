package com.depromeet.knockknock.ui.editroomdetails

import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction

sealed class EditRoomDetailsNavigationAction {
    object NavigateToSetProfileImage: EditRoomDetailsNavigationAction()
    object NavigateToHome: EditRoomDetailsNavigationAction()
}