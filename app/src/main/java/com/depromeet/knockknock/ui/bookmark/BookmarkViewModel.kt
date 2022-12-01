package com.depromeet.knockknock.ui.bookmark

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.model.FilterType
import com.depromeet.knockknock.ui.mypage.MypageNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
) : BaseViewModel(), BookmarkActionHandler {

    private val TAG = "AlarmRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<BookmarkNavigationAction> = MutableSharedFlow<BookmarkNavigationAction>()
    val navigationHandler: SharedFlow<BookmarkNavigationAction> = _navigationHandler.asSharedFlow()

    private val _filterTypeState: MutableStateFlow<FilterType> = MutableStateFlow<FilterType>(FilterType.ALL)
    val filterTypeState: StateFlow<FilterType> = _filterTypeState.asStateFlow()

    override fun onBookmarkEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkEdit)
        }
    }


    override fun onFilterAllClicked() {
        baseViewModelScope.launch {
            _filterTypeState.value = FilterType.ALL
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilterAll)
        }
    }

    override fun onFilterRoomClicked() {
        baseViewModelScope.launch {
            _filterTypeState.value = FilterType.ROOM
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilterRoom)
        }
    }

    override fun onFilterPeriodClicked() {
        baseViewModelScope.launch {
            _filterTypeState.value = FilterType.PERIOD
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilterPeriod)
        }
    }
}