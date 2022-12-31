package com.depromeet.knockknock.ui.settingroomforuser

import com.depromeet.domain.model.Category
import com.depromeet.domain.model.Group
import com.depromeet.domain.model.Member
import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmroomsearch.model.AlarmRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class SettingRoomForUserViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), SettingRoomForUserActionHandler {

    private val TAG = "SettingRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<SettingRoomForUserNavigationAction> =
        MutableSharedFlow<SettingRoomForUserNavigationAction>()
    val navigationHandler: SharedFlow<SettingRoomForUserNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _categoryInfo: MutableStateFlow<String> = MutableStateFlow<String>("없음")
    val categoryInfo: StateFlow<String> = _categoryInfo.asStateFlow()

    private val _receivedRoomId : MutableStateFlow<Int> = MutableStateFlow<Int>(1)
    val receivedRoomId : StateFlow<Int> = _receivedRoomId.asStateFlow()

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

    private val _roomMemberList : MutableStateFlow<List<Member>> = MutableStateFlow(emptyList())
    val roomMemberList : StateFlow<List<Member>> = _roomMemberList.asStateFlow()

    init{

        baseViewModelScope.launch {
//            mainRepository.getGroup(id = _receivedRoomId.value)
//                .onSuccess {
//                    _roomInfo.emit(it)
//                    _roomMemberList.emit(it.members)
//                }
//                .onError {
//                    val testGroup = Group(
//                        background_image_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                        category = Category(
//                            id = 2,
//                            content = "스터디",
//                            emoji = "1"
//                        ),
//                        description = "무슨무슨 방입니다",
//                        group_id = 17,
//                        group_type = "OPEN",
//                        ihost = true,
//                        members = listOf(
//                            Member(
//                                nick_name = "이영준",
//                                profile_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                                user_id = 1
//                            )
//                        ),
//                        public_access = true,
//                        thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                        title = "방 이름"
//                    )
//                    _roomInfo.emit(testGroup)
//                    _roomMemberList.emit(testGroup.members)
//                }

            val testGroup = Group(
                background_image_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                category = Category(
                    id = 3,
                    content = "취업",
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
            _roomInfo.emit(testGroup)
            _roomMemberList.emit(testGroup.members)
        }

    }



    override fun onAddMemberClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomForUserNavigationAction.NavigateToAddMember(roomId = roomInfo.value.group_id))
        }
    }


    override fun onLinkClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomForUserNavigationAction.NavigateToLink(roomId = roomInfo.value.group_id))
        }
    }

}