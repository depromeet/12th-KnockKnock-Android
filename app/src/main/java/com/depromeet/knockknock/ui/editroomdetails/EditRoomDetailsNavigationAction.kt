package com.depromeet.knockknock.ui.editroomdetails

import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction

sealed class EditRoomDetailsNavigationAction {
    object NavigateToSettingRoom: EditRoomDetailsNavigationAction()
    object NavigateToEditBackground:EditRoomDetailsNavigationAction()
    object NavigateToEditThumbnail:EditRoomDetailsNavigationAction()
    class NavigateToSetBackgroundFromList(val backgroundUrl : String): EditRoomDetailsNavigationAction()
    class NavigateToSetThumbnailFromList(val thumbnailUrl : String): EditRoomDetailsNavigationAction()
}