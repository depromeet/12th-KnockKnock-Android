package com.depromeet.knockknock.ui.bookmark

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.mypage.MypageNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
) : BaseViewModel(), BookmarkActionHandler {

    private val TAG = "AlarmRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<BookmarkNavigationAction> = MutableSharedFlow<BookmarkNavigationAction>()
    val navigationHandler: SharedFlow<BookmarkNavigationAction> = _navigationHandler.asSharedFlow()

    override fun onBookmarkEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkEdit)
        }
    }

    override fun onFilterClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(BookmarkNavigationAction.NavigateToBookmarkFilter)
        }
    }

}