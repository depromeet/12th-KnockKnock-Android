package com.depromeet.knockknock.ui.friendlist

import com.depromeet.domain.model.Friend
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.BookmarkNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class FriendListViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), FriendListActionHandler {

    private val TAG = "AlarmRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<FriendListNavigationAction> = MutableSharedFlow<FriendListNavigationAction>()
    val navigationHandler: SharedFlow<FriendListNavigationAction> = _navigationHandler.asSharedFlow()

    val searchQuery: MutableStateFlow<String> = MutableStateFlow<String>("")

    private val _friendList: MutableStateFlow<List<Friend>> = MutableStateFlow(emptyList())
    val friendList: StateFlow<List<Friend>> = _friendList.asStateFlow()

    fun getFriends() {
        baseViewModelScope.launch {
            mainRepository.getRelations()
                .onSuccess {
                    _friendList.value = it.friend_list
                }
        }
    }

    fun deleteFriend(id: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(FriendListNavigationAction.NavigateToDeleteSuccess)
        }
    }

    fun declareFriend(id: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(FriendListNavigationAction.NavigateToDeclareSuccess)
        }
    }

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

    override fun onAddFriendClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(FriendListNavigationAction.NavigateToAddFriends)
        }
    }

}