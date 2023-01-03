package com.depromeet.knockknock.ui.alarmroomexplore

import com.depromeet.domain.model.Category
import com.depromeet.domain.model.GroupBriefInfo
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

    private val _clickedCategoryId : MutableStateFlow<Int> = MutableStateFlow<Int>(2)
    val clickedCategoryId : StateFlow<Int> = _clickedCategoryId.asStateFlow()

    private val _roomList: MutableStateFlow<List<GroupContent>> = MutableStateFlow(emptyList())
    val roomList: StateFlow<List<GroupContent>> = _roomList.asStateFlow()

    private val _categoryList: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList.asStateFlow()

    private val _popularRoomList: MutableStateFlow<List<GroupBriefInfo>> = MutableStateFlow(emptyList())
    val popularRoomList: StateFlow<List<GroupBriefInfo>> = _popularRoomList.asStateFlow()

    init {
        baseViewModelScope.launch {
            mainRepository.getOpenGroups(1,0,10)
                .onSuccess { response ->
                    _roomList.emit(
                        (response.groupContent)
                    )
                }
        }

        baseViewModelScope.launch {
            mainRepository.getGroupCategories()
                .onSuccess { response ->
                    _categoryList.emit(listOf(Category(
                        id = 1,
                        content = "전체",
                        emoji = "\uD83D\uDC40",
                    )) + response.categories)
                }
        }

        baseViewModelScope.launch {
            mainRepository.getFamousGroups()
                .onSuccess { response->
                    _popularRoomList.emit(response.group_brief_infos)
                }
        }
    }

    fun getGroups(){
        baseViewModelScope.launch {
            mainRepository.getOpenGroups(1,0,10)
                .onSuccess { response ->
                    _roomList.emit(response.groupContent)
                }
        }
    }

    fun getCategory(){
        baseViewModelScope.launch {
            mainRepository.getGroupCategories()
                .onSuccess { response ->
                    _categoryList.emit(listOf(Category(
                        id = 1,
                        content = "전체",
                        emoji = "\uD83D\uDC40",
                    )) + response.categories)
                }
        }
    }

    fun getFamousGroups(){
        baseViewModelScope.launch {
            mainRepository.getFamousGroups()
                .onSuccess { response->
                    _popularRoomList.emit(response.group_brief_infos)
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