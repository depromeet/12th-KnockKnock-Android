package com.depromeet.knockknock.ui.addfriend

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.BookmarkNavigationAction
import com.depromeet.knockknock.ui.friendlist.FriendListActionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel @Inject constructor(
) : BaseViewModel(), AddFriendActionHandler {

    private val TAG = "AlarmRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<AddFriendNavigationAction> = MutableSharedFlow<AddFriendNavigationAction>()
    val navigationHandler: SharedFlow<AddFriendNavigationAction> = _navigationHandler.asSharedFlow()

    val searchQuery: MutableStateFlow<String> = MutableStateFlow<String>("")

    override fun onAddFriendClicked(userIdx: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(AddFriendNavigationAction.NavigateToAddFriend(userIdx = userIdx))
        }
    }

}