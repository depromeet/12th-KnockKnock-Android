package com.depromeet.knockknock.ui.friendlist

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.BookmarkNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendListViewModel @Inject constructor(
) : BaseViewModel(), FriendListActionHandler {

    private val TAG = "AlarmRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<FriendListNavigationAction> = MutableSharedFlow<FriendListNavigationAction>()
    val navigationHandler: SharedFlow<FriendListNavigationAction> = _navigationHandler.asSharedFlow()

    val searchQuery: MutableStateFlow<String> = MutableStateFlow<String>("")

    override fun onLinkedClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(FriendListNavigationAction.NavigateToLink)
        }
    }



    override fun onFriendMoreClicked(userIdx: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(FriendListNavigationAction.NavigateToFriendMore(userIdx = userIdx))
        }
    }

}