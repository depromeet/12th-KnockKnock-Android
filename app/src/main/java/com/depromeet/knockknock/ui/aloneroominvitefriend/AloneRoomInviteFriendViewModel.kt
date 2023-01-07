package com.depromeet.knockknock.ui.aloneroominvitefriend

import com.depromeet.domain.model.Friend
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.aloneroommakecategory.AloneRoomMakeCategoryNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AloneRoomInviteFriendViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), AloneRoomInviteFriendActionHandler {

    private val TAG = "InviteFriendViewModel"

    private val _navigationHandler: MutableSharedFlow<AloneRoomInviteFriendNavigationAction> = MutableSharedFlow<AloneRoomInviteFriendNavigationAction>()
    val navigationHandler: SharedFlow<AloneRoomInviteFriendNavigationAction> = _navigationHandler.asSharedFlow()

    private val _saveBtnEnable: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val saveBtnEnable: StateFlow<Boolean> = _saveBtnEnable.asStateFlow()

    private val _clickUser: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val clickUser: StateFlow<Int> = _clickUser.asStateFlow()

    private val _onSaveSuccess: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val onSaveSuccess: SharedFlow<Boolean> = _onSaveSuccess.asSharedFlow()

    private val _friendList : MutableStateFlow<List<Friend>> = MutableStateFlow(emptyList())
    val friendList : StateFlow<List<Friend>> = _friendList.asStateFlow()
    val inviteUserList = mutableListOf<Int>()
    val searchQuery: MutableStateFlow<String> = MutableStateFlow<String>("")

    var inputRoomName = MutableStateFlow<String>("")
    var inputRoomDescription = MutableStateFlow<String>("")
    var thumbnailImg = MutableStateFlow<String>("https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23")
    var backgroundImg = MutableStateFlow<String>("https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23")
    var group_category_id = MutableStateFlow<Int>(0)
    var isRoomUnpublic = MutableStateFlow<Boolean>(false)

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
                if(it.isNotEmpty()) mainRepository.getRelations()
                    .onSuccess { response ->
                        _friendList.emit(response.friend_list.filter { friend -> friend.nickname.contains(it) }) }
                else {
                    mainRepository.getRelations()
                        .onSuccess { response ->
                            _friendList.emit(response.friend_list) }
                }
            }
        }
    }

    override fun onSkipCLicked() {
        baseViewModelScope.launch {
            mainRepository.postGroupOpen(
                title = inputRoomName.value,
                description = inputRoomDescription.value,
                public_access = !isRoomUnpublic.value,
                background_image_path = backgroundImg.value,
                thumbnail_path = thumbnailImg.value,
                category_id = group_category_id.value,
                member_ids = emptyList()
            ).onSuccess {
                _navigationHandler.emit(AloneRoomInviteFriendNavigationAction.NavigateToGeneratedRoom(groupId = it.group_id))
            }
        }
    }

    override fun onCompleteClicked() {
        baseViewModelScope.launch {
            mainRepository.postGroupOpen(
                title = inputRoomName.value,
                description = inputRoomDescription.value,
                public_access = !isRoomUnpublic.value,
                background_image_path = backgroundImg.value,
                thumbnail_path = thumbnailImg.value,
                category_id = group_category_id.value,
                member_ids = inviteUserList
            ).onSuccess {
                _navigationHandler.emit(AloneRoomInviteFriendNavigationAction.NavigateToGeneratedRoom(groupId = it.group_id))
            }
        }
    }

}