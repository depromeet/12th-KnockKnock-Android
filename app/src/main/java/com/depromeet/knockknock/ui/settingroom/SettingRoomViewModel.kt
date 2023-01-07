package com.depromeet.knockknock.ui.settingroom

import com.depromeet.domain.model.Category
import com.depromeet.domain.model.Group
import com.depromeet.domain.model.Member
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class SettingRoomViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), SettingRoomActionHandler {

    private val TAG = "SettingRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<SettingRoomNavigationAction> =
        MutableSharedFlow<SettingRoomNavigationAction>()
    val navigationHandler: SharedFlow<SettingRoomNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _receivedRoomId: MutableStateFlow<Int> = MutableStateFlow<Int>(1)
    val receivedRoomId: StateFlow<Int> = _receivedRoomId.asStateFlow()

    private val _roomInfo: MutableStateFlow<Group> = MutableStateFlow(
        Group(
            background_image_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            category = Category(
                id = 2,
                content = "스터디",
                emoji = "1"
            ),
            description = "무슨무슨 방입니다",
            group_id = 17,
            group_type = "OPEN",
            ihost = true,
            members = listOf(
                Member(
                    nick_name = "이영준",
                    profile_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                    user_id = 1
                )
            ),
            public_access = true,
            thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            title = "방 이름"
        )
    )
    val roomInfo: StateFlow<Group> = _roomInfo.asStateFlow()

    private val _roomMemberList: MutableStateFlow<List<Member>> = MutableStateFlow(emptyList())
    val roomMemberList: StateFlow<List<Member>> = _roomMemberList.asStateFlow()

    init {
        baseViewModelScope.launch {
            mainRepository.getGroup(id = _receivedRoomId.value)
                .onSuccess {
                    _roomInfo.emit(it)
                    _roomMemberList.emit(it.members)
                }

        }
    }

    fun getGroupInfo() {
        baseViewModelScope.launch {
            mainRepository.getGroup(id = _receivedRoomId.value)
                .onSuccess {
                    _roomInfo.emit(it)
                    _roomMemberList.emit(it.members)
                }
        }

    }

    override fun onCategoryClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(
                SettingRoomNavigationAction.NavigateToCategory(
                    id = _roomInfo.value.group_id,
                    title = _roomInfo.value.title,
                    description = _roomInfo.value.description,
                    thumbnailPath = _roomInfo.value.thumbnail_path,
                    backgroundPath = _roomInfo.value.background_image_path,
                    publicAccess = _roomInfo.value.public_access,
                    categoryId = _roomInfo.value.category.id
                )
            )
        }
    }

    override fun onEditDetailClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomNavigationAction.NavigateToEditDetail(
                id = _roomInfo.value.group_id,
                title = _roomInfo.value.title,
                description = _roomInfo.value.description,
                thumbnailPath = _roomInfo.value.thumbnail_path,
                backgroundPath = _roomInfo.value.background_image_path,
                publicAccess = _roomInfo.value.public_access,
                categoryId = _roomInfo.value.category.id
            ))
        }
    }

    override fun onAddMemberClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomNavigationAction.NavigateToAddMember(roomId = roomInfo.value.group_id))
        }
    }

    override fun onDeleteClicked() {
        baseViewModelScope.launch {
            mainRepository.deleteGroup(receivedRoomId.value)
                .onSuccess {
                    _navigationHandler.emit(SettingRoomNavigationAction.NavigateToRemove(roomId = roomInfo.value.group_id))
                }

        }
    }

    override fun onLinkClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomNavigationAction.NavigateToLink(roomId = roomInfo.value.group_id))
        }
    }

    override fun onExportClicked(userId: Int) {
        baseViewModelScope.launch {
            mainRepository.removeMember(receivedRoomId.value, userId)
                .onSuccess {
                    _navigationHandler.emit(
                        SettingRoomNavigationAction.NavigateToExportMember(
                            userId = userId
                        )
                    )
                    //멤버 내쫓고 다시 그룹 정보 가져오기
                    getGroupInfo()
                }

        }
    }

}