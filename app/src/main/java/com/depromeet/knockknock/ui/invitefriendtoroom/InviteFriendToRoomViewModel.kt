package com.depromeet.knockknock.ui.invitefriendtoroom

import com.depromeet.domain.model.Friend
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.addfriend.AddFriendNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteFriendToRoomViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), InviteFriendToRoomActionHandler {

    private val TAG = "InviteFriendViewModel"

    private val _navigationHandler: MutableSharedFlow<InviteFriendToRoomNavigationAction> = MutableSharedFlow<InviteFriendToRoomNavigationAction>()
    val navigationHandler: SharedFlow<InviteFriendToRoomNavigationAction> = _navigationHandler.asSharedFlow()

    private val _saveBtnEnable: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val saveBtnEnable: StateFlow<Boolean> = _saveBtnEnable.asStateFlow()

    private val _clickUser: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val clickUser: StateFlow<Int> = _clickUser.asStateFlow()

    var groupId = MutableStateFlow<Int>(0)

    private val _onSaveSuccess: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val onSaveSuccess: SharedFlow<Boolean> = _onSaveSuccess.asSharedFlow()

    private val _friendList : MutableStateFlow<List<Friend>> = MutableStateFlow(listOf(
        Friend(
        1,
        nickname = "영준",
        profile_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23"
    )))
    val friendList : StateFlow<List<Friend>> = _friendList.asStateFlow()
    val inviteUserList = mutableListOf<Int>()

    val searchQuery: MutableStateFlow<String> = MutableStateFlow<String>("")

    init{
        baseViewModelScope.launch {
            mainRepository.getRelations()
                .onSuccess { response ->
                    _friendList.emit(response.friend_list) }
        }
    }


    override fun onInviteFriendClicked(userIdx: Int, isChecked: Boolean) {
        if(isChecked) inviteUserList.add(userIdx)
        else inviteUserList.remove(userIdx)
        _saveBtnEnable.value = inviteUserList.size > 0

        baseViewModelScope.launch {
            _saveBtnEnable.emit(_saveBtnEnable.value)
        }

        baseViewModelScope.launch {
            searchQuery.debounce(0).collect {
                if(it.isNotEmpty()) {
                    mainRepository.getRelations()
                        .onSuccess { response ->
                            _friendList.emit(response.friend_list.filter { friend -> friend.nickname.contains(it) }) }
                }
                else {
                    mainRepository.getRelations()
                        .onSuccess { response ->
                            _friendList.emit(response.friend_list) }
                }
            }
        }
    }

    override fun onCompleteClicked() {
        baseViewModelScope.launch {
            mainRepository.postAddMember(id = groupId.value, member_ids = inviteUserList)
                .onSuccess {
                    _onSaveSuccess.emit(true)
                }
        }
    }

}