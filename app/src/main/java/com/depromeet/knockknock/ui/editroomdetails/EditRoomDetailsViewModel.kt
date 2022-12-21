package com.depromeet.knockknock.ui.editroomdetails

import com.depromeet.knockknock.ui.friendlist.FriendListActionHandler
import com.depromeet.knockknock.ui.friendlist.FriendListNavigationAction


import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.BookmarkNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRoomDetailsViewModel @Inject constructor(
) : BaseViewModel(), EditRoomDetailsActionHandler {

    private val TAG = "EditRoomDetailsViewModel"

    private val _navigationHandler: MutableSharedFlow<EditRoomDetailsNavigationAction> = MutableSharedFlow<EditRoomDetailsNavigationAction>()
    val navigationHandler: SharedFlow<EditRoomDetailsNavigationAction> = _navigationHandler.asSharedFlow()


}