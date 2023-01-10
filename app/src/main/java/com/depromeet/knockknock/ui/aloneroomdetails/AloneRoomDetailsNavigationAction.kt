package com.depromeet.knockknock.ui.aloneroomdetails

sealed class AloneRoomDetailsNavigationAction {
    object NavigateToAloneRoomInviteFriend: AloneRoomDetailsNavigationAction()
    object NavigateToEditBackground:AloneRoomDetailsNavigationAction()
    object NavigateToEditThumbnail:AloneRoomDetailsNavigationAction()
    class NavigateToSetBackgroundFromList(val backgroundUrl : String): AloneRoomDetailsNavigationAction()
    class NavigateToSetThumbnailFromList(val thumbnailUrl : String): AloneRoomDetailsNavigationAction()
}