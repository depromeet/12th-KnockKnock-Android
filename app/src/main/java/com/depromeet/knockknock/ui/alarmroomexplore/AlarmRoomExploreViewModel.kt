package com.depromeet.knockknock.ui.alarmroomexplore

import android.widget.Toast
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.depromeet.domain.model.Category
import com.depromeet.domain.model.GroupBriefInfo
import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.model.Notification
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.createAlarmRoomPager
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.createAlarmRoomHistoryBundlePager
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
    val clickedCategoryId : StateFlow<Int> = _clickedCategoryId.asStateFlow()

    var roomList: Flow<PagingData<GroupContent>> = emptyFlow()
    var initCategory : MutableStateFlow<Int> = MutableStateFlow(1)

    private val _categoryList: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList.asStateFlow()

    private val _popularRoomList: MutableStateFlow<List<GroupBriefInfo>> = MutableStateFlow(emptyList())
    val popularRoomList: StateFlow<List<GroupBriefInfo>> = _popularRoomList.asStateFlow()

    init {
        getGroups()

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
        roomList = createAlarmRoomPager(
            mainRepository = mainRepository,
            category = initCategory,
        ).flow.cachedIn(baseViewModelScope)
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

    override fun onRoomClicked(roomId: Int) {
        //println("clicked ${roomId}")
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomExploreNavigationAction.NavigateToRoom(roomId = roomId))
        }
    }
}