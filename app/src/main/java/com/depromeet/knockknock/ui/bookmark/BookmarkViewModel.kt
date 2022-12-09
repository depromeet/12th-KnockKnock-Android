package com.depromeet.knockknock.ui.bookmark

import com.depromeet.knockknock.base.BaseViewModel
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

    private val _roomClicked: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val roomClicked: StateFlow<Int> = _roomClicked

    private val _periodClicked: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val periodClicked: StateFlow<Int> = _periodClicked

    private val _filterChecked: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val filterChecked: StateFlow<Boolean> = _filterChecked

    override fun onBookmarkEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkEdit)
        }
    }


    override fun onFilterResetClicked() {
        baseViewModelScope.launch {
            _roomClicked.emit(0)
            _periodClicked.emit(0)
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilterReset)
        }
    }

    override fun onFilterRoomClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilterRoom)
        }
    }

    fun setRoomFilter(roomCheckCount: Int) = baseViewModelScope.launch {
        _roomClicked.emit(roomCheckCount)

        if(roomClicked.value == 0 && periodClicked.value == 0) {
            _filterChecked.emit(false)
        } else {
            _filterChecked.emit(true)
        }
    }

    override fun onFilterPeriodClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilterPeriod)
        }
    }

    fun setPeriodFilter(periodCheckCount: Int) = baseViewModelScope.launch {
        _periodClicked.emit(periodCheckCount)

        if(roomClicked.value == 0 && periodClicked.value == 0) {
            _filterChecked.emit(false)
        } else {
            _filterChecked.emit(true)
        }
    }

    override fun onReactionClicked(bookmarkIdx: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToReaction(bookmarkIdx))
        }
    }
}