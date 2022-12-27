package com.depromeet.knockknock.ui.invitefriendtoroom

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.editbookmark.EditBookmarkNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteFriendToRoomViewModel @Inject constructor(
) : BaseViewModel(), InviteFriendToRoomActionHandler {

    private val TAG = "InviteFriendViewModel"

    private val _navigationHandler: MutableSharedFlow<InviteFriendToRoomNavigationAction> = MutableSharedFlow<InviteFriendToRoomNavigationAction>()
    val navigationHandler: SharedFlow<InviteFriendToRoomNavigationAction> = _navigationHandler.asSharedFlow()

    private val _saveBtnEnable: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val saveBtnEnable: StateFlow<Boolean> = _saveBtnEnable.asStateFlow()

    private val _clickUser: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val clickUser: StateFlow<Int> = _clickUser.asStateFlow()

    private val _onSaveSuccess: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val onSaveSuccess: SharedFlow<Boolean> = _onSaveSuccess.asSharedFlow()

    val inviteUserList = mutableListOf<Int>()

    val searchQuery: MutableStateFlow<String> = MutableStateFlow<String>("")


    override fun onInviteFriendClicked(userIdx: Int, isChecked: Boolean) {
        if(isChecked) inviteUserList.add(userIdx)
        else inviteUserList.remove(userIdx)
        _saveBtnEnable.value = inviteUserList.size > 0

        baseViewModelScope.launch {
            //_onSaveSuccess.emit(true)
            println("onsavesuccess is ${saveBtnEnable.value}")
            _saveBtnEnable.emit(_saveBtnEnable.value)
            _clickUser.emit(userIdx)
        }
    }

    override fun onCompleteClicked() {
        baseViewModelScope.launch {
            _onSaveSuccess.emit(true)
        }
    }
}