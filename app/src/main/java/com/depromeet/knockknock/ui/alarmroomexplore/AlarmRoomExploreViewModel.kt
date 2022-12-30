package com.depromeet.knockknock.ui.alarmroomexplore

import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomExploreViewModel @Inject constructor(
) : BaseViewModel(), AlarmRoomExploreActionHandler {

    private val TAG = "AlarmRoomSearchViewModel"

    private val _navigationHandler: MutableSharedFlow<AlarmRoomExploreNavigationAction> = MutableSharedFlow<AlarmRoomExploreNavigationAction>()
    val navigationHandler: SharedFlow<AlarmRoomExploreNavigationAction> = _navigationHandler.asSharedFlow()

    private val _roomIsPublic : MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val roomIsPublic : StateFlow<Boolean> = _roomIsPublic.asStateFlow()

    private val _clickedCategoryId : MutableStateFlow<Int> = MutableStateFlow<Int>(1)
    val clickedCategoryName : StateFlow<Int> = _clickedCategoryId.asStateFlow()


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