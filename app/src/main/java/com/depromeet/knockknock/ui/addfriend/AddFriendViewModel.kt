package com.depromeet.knockknock.ui.addfriend

import android.util.Log
import com.depromeet.domain.model.User
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.BookmarkNavigationAction
import com.depromeet.knockknock.ui.friendlist.FriendListActionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), AddFriendActionHandler {

    private val TAG = "AlarmRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<AddFriendNavigationAction> = MutableSharedFlow<AddFriendNavigationAction>()
    val navigationHandler: SharedFlow<AddFriendNavigationAction> = _navigationHandler.asSharedFlow()

    val userInputState: MutableStateFlow<String> = MutableStateFlow<String>("")

    private val _userList: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val userList: StateFlow<List<User>> = _userList.asStateFlow()

    init {
        baseViewModelScope.launch {
            userInputState.debounce(500).collect {
                if(it.isNotEmpty()) {
                    mainRepository.getUsersNickname(it)
                        .onSuccess { response ->
                            _userList.emit(response.user_list.filter { user -> !user.friend }) }
                } else {
                    _userList.value = emptyList()
                }
            }
        }
    }

    fun addFriend(userIdx: Int, nickname: String) {
        baseViewModelScope.launch {
            mainRepository.postRelations(user_id = userIdx)
                .onSuccess {
                    _navigationHandler.emit(AddFriendNavigationAction.NavigateToAddSuccess(nickname = nickname))
                }
        }
    }

    override fun onAddFriendClicked(userIdx: Int, nickname: String) {
        baseViewModelScope.launch {
            _navigationHandler.emit(AddFriendNavigationAction.NavigateToAddFriend(userIdx = userIdx, nickname = nickname))
        }
    }

}