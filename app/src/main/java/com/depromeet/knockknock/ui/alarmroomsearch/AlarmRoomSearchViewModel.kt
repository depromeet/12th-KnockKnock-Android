package com.depromeet.knockknock.ui.alarmroomsearch

import com.depromeet.domain.model.Category
import com.depromeet.domain.model.Friend
import com.depromeet.domain.model.GroupBriefInfo
import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmroomsearch.model.AlarmRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomSearchViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), AlarmRoomSearchActionHandler {

    private val TAG = "AlarmRoomSearchViewModel"

    private val _navigationHandler: MutableSharedFlow<AlarmRoomSearchNavigationAction> =
        MutableSharedFlow<AlarmRoomSearchNavigationAction>()
    val navigationHandler: SharedFlow<AlarmRoomSearchNavigationAction> =
        _navigationHandler.asSharedFlow()

    val roomInputState: MutableStateFlow<String> = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)

    private val _roomIsPublic: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val roomIsPublic: StateFlow<Boolean> = _roomIsPublic.asStateFlow()

    private val _roomList: MutableStateFlow<List<GroupContent>> = MutableStateFlow(emptyList())
//        listOf(
//            GroupContent(
//                category = Category(
//                    content = "취업",
//                    emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                    id = 1
//                ),
//                description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
//                group_id = 1,
//                group_type = "OPEN",
//                member_count = 10,
//                public_access = true,
//                thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                title = "방 제목"
//            ),
//            GroupBriefInfo(
//                category = com.depromeet.domain.model.Category(
//                    content = "취업",
//                    emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                    id = 1
//                ),
//                description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
//                group_id = 2,
//                group_type = "OPEN",
//                member_count = 10,
//                public_access = true,
//                thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                title = "방 제목"
//            ),
//
//            GroupBriefInfo(
//                category = com.depromeet.domain.model.Category(
//                    content = "취업",
//                    emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                    id = 1
//                ),
//                description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
//                group_id = 3,
//                group_type = "OPEN",
//                member_count = 10,
//                public_access = true,
//                thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                title = "이상한 제목"
//            )
//        )
//    )

    val roomList: StateFlow<List<GroupContent>> = _roomList.asStateFlow()


    init {
        baseViewModelScope.launch {
            roomInputState.debounce(0).collectLatest {
                onEditTextCount(it.codePointCount(0, it.length))
            }
        }

//        baseViewModelScope.launch {
//            roomInputState.debounce(500).collect{
//                println("roomInputState is ${roomInputState.value}")
//                if (it.isNotEmpty()) {
//                    var roomListcopy = _roomList
//                    _roomList.emit(roomListcopy.value.filter { room ->
//                        room.title.contains(it) })
//                    roomListcopy = _roomList
//                println("filtered room is ${_roomList}")}
//                else {
//                    _roomList.emit(roomList.value)
//                }
//            }
//        }

        baseViewModelScope.launch {
            roomInputState.debounce(500).collect {
                if(it.isNotEmpty()) {
                    mainRepository.getOpenGroups(1,0,10)
                        .onSuccess { response ->
                            _roomList.emit(response.groupContent.filter { room -> room.title.contains(it) }) }
                } else {
                    mainRepository.getOpenGroups(1,0,10)
                        .onSuccess { response ->
                            _roomList.emit(response.groupContent) }
                }
            }
        }
    }

    private fun onEditTextCount(count: Int) {
        baseViewModelScope.launch {
            editTextMessageCountEvent.value = count
        }
    }

    override fun onAlarmRoomEditTextClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomSearchNavigationAction.NavigateToHidePopularCategory)
        }
    }

    override fun onAlarmRoomGenerateClicked(roomName: String) {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomSearchNavigationAction.NavigateToMakeRoom(roomName = roomName))
        }
    }

    override fun onRoomClicked(roomId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomSearchNavigationAction.NavigateToRoom(roomId = roomId))
        }
    }


}