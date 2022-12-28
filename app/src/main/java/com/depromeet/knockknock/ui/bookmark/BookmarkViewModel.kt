package com.depromeet.knockknock.ui.bookmark

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.depromeet.domain.model.Notification
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.adapter.createNotificationPager
import com.depromeet.knockknock.ui.bookmark.model.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), BookmarkActionHandler {

    private val TAG = "BookmarkViewModel"

    private val _navigationHandler: MutableSharedFlow<BookmarkNavigationAction> = MutableSharedFlow<BookmarkNavigationAction>()
    val navigationHandler: SharedFlow<BookmarkNavigationAction> = _navigationHandler.asSharedFlow()

    private val _roomClicked: MutableStateFlow<List<Int>> = MutableStateFlow<List<Int>>(emptyList())
    val roomClicked: StateFlow<List<Int>> = _roomClicked

    private val _periodClicked: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val periodClicked: StateFlow<Int> = _periodClicked

    private val _filterChecked: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val filterChecked: StateFlow<Boolean> = _filterChecked

    var bookmarkList: Flow<PagingData<Notification>> = emptyFlow()

    init {
        bookmarkList = createNotificationPager(mainRepository).flow.cachedIn(baseViewModelScope)
    }

    override fun onBookmarkEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkEdit)
        }
    }


    override fun onFilterResetClicked() {
        baseViewModelScope.launch {
            _roomClicked.value = emptyList()
            _periodClicked.value = 0
            _filterChecked.value = false
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilterReset)
        }
    }

    override fun onFilterRoomClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilterRoom)
        }
    }

    fun setRoomFilter(roomFilter: List<Int>) = baseViewModelScope.launch {
        _roomClicked.emit(roomFilter)

        if(roomClicked.value.size == 0 && periodClicked.value == 0) _filterChecked.value = false
        else _filterChecked.value = true
    }

    override fun onFilterPeriodClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilterPeriod)
        }
    }

    fun setPeriodFilter(periodCheckCount: Int) = baseViewModelScope.launch {
        _periodClicked.value = periodCheckCount

        if(roomClicked.value.size == 0 && periodClicked.value == 0) _filterChecked.value = false
        else _filterChecked.value = true
    }

    override fun onReactionClicked(bookmarkIdx: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToReaction(bookmarkIdx))
        }
    }

    override fun onNotificationClicked(bookmarkIdx: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToNotificationDetail(notification_id = bookmarkIdx))
        }
    }
}