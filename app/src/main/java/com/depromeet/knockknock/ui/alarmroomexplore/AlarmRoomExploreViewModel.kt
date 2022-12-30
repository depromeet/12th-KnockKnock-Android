package com.depromeet.knockknock.ui.alarmroomexplore

import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomExploreViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), AlarmRoomExploreActionHandler {

    private val TAG = "AlarmRoomExploreViewModel"

    private val _navigationHandler: MutableSharedFlow<AlarmRoomExploreNavigationAction> = MutableSharedFlow<AlarmRoomExploreNavigationAction>()
    val navigationHandler: SharedFlow<AlarmRoomExploreNavigationAction> = _navigationHandler.asSharedFlow()

    private val _roomIsPublic : MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val roomIsPublic : StateFlow<Boolean> = _roomIsPublic.asStateFlow()

    private val _clickedCategoryId : MutableStateFlow<Int> = MutableStateFlow<Int>(1)
    val clickedCategoryName : StateFlow<Int> = _clickedCategoryId.asStateFlow()

    private val _roomList: MutableStateFlow<List<GroupContent>> = MutableStateFlow(emptyList())
    val roomList: StateFlow<List<GroupContent>> = _roomList.asStateFlow()

    init {
        baseViewModelScope.launch {
            mainRepository.getOpenGroups(1,0,10)
                .onSuccess { response ->
                    _roomList.emit(response.groupContent)
                }
        }
    }

    override fun onAlarmRoomEditTextClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomExploreNavigationAction.NavigateToAlarmRoomSearch)
        }
    }

    override fun onCategoryClicked(categoryId: Int) {
        baseViewModelScope.launch {
            _clickedCategoryId.emit(categoryId)
        }
    }

    override fun onPopularRoomClicked(roomId: Int) {
    }


    override fun onRoomClicked(roomId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomExploreNavigationAction.NavigateToRoom(roomId = roomId))
        }
    }


}