package com.depromeet.knockknock.ui.alarmroomjoined

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmroomjoined.adapter.createAlarmRoomJoinedPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomJoinedViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), AlarmRoomJoinedActionHandler {

    private val TAG = "AlarmRoomSearchViewModel"

    private val _navigationHandler: MutableSharedFlow<AlarmRoomJoinedNavigationAction> = MutableSharedFlow<AlarmRoomJoinedNavigationAction>()
    val navigationHandler: SharedFlow<AlarmRoomJoinedNavigationAction> = _navigationHandler.asSharedFlow()

    val searchQuery: MutableStateFlow<String> = MutableStateFlow<String>("")

    private val _roomIsPublic : MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val roomIsPublic : StateFlow<Boolean> = _roomIsPublic.asStateFlow()

    private val _roomAllClicked : MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val roomAllClicked : StateFlow<Boolean> = _roomAllClicked.asStateFlow()

    private val _roomFriendClicked : MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val roomFriendClicked : StateFlow<Boolean> = _roomFriendClicked.asStateFlow()

    private val _roomAloneClicked : MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val roomAloneClicked : StateFlow<Boolean> = _roomAloneClicked.asStateFlow()

    var currentRoomCount : MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    var _joinedRoomList : MutableStateFlow<List<GroupContent>> = MutableStateFlow(emptyList())
    var joinedRoomList: Flow<PagingData<GroupContent>> = emptyFlow()
    var initJoinedRoomType : MutableStateFlow<String> = MutableStateFlow("ALL")

    init{
        getJoinedGroups()
    }

    fun getJoinedGroups(){
//            joinedRoomList = createAlarmRoomJoinedPager(
//                mainRepository = mainRepository,
//                roomType = initJoinedRoomType
//            ).flow.cachedIn(baseViewModelScope)\
        baseViewModelScope.launch {
            mainRepository.getJoinedGroups("ALL",0,30)
                .onSuccess {
                    _joinedRoomList.emit(it.groupContent)
                    if (_joinedRoomList.value.size>0){
                        currentRoomCount.value = 1
                    }
                }
        }

    }

    fun getJoinedGroupCount(){
        baseViewModelScope.launch {
            println("roomlist num is ${joinedRoomList.count()}")
            currentRoomCount.emit(joinedRoomList.count())
        }
    }

    override fun onRoomTypeAllClicked() {
        baseViewModelScope.launch { 
            _roomAllClicked.emit(true)
            _roomFriendClicked.emit(false)
            _roomAloneClicked.emit(false)
        }
    }

    override fun onRoomTypeFriendClicked() {
        baseViewModelScope.launch {
            _roomAllClicked.emit(false)
            _roomFriendClicked.emit(true)
            _roomAloneClicked.emit(false)
        }
    }

    override fun onRoomTypeAloneClicked() {
        baseViewModelScope.launch {
            _roomAllClicked.emit(false)
            _roomFriendClicked.emit(false)
            _roomAloneClicked.emit(true)
        }
    }


    override fun onRoomClicked(roomId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomJoinedNavigationAction.NavigateToRoom(roomId = roomId))
        }
    }

    override fun onMakeRoomClicked(){
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomJoinedNavigationAction.NavigateToMakeRoom)
        }
    }


}