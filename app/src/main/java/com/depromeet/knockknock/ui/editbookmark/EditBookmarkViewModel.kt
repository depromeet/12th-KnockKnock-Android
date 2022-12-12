package com.depromeet.knockknock.ui.editbookmark

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.editbookmark.model.EditBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBookmarkViewModel @Inject constructor(
) : BaseViewModel(), EditBookmarkActionHandler {

    private val TAG = "EditBookmarkViewModel"

    private val _navigationHandler: MutableSharedFlow<EditBookmarkNavigationAction> = MutableSharedFlow<EditBookmarkNavigationAction>()
    val navigationHandler: SharedFlow<EditBookmarkNavigationAction> = _navigationHandler.asSharedFlow()

    private val _bookmarkList: MutableStateFlow<List<EditBookmark>> = MutableStateFlow<List<EditBookmark>>(emptyList())
    val bookmarkList: StateFlow<List<EditBookmark>> = _bookmarkList

    private val _enableEditBtn: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val enableEditBtn: StateFlow<Boolean> = _enableEditBtn

    val deleteBookmarkList = mutableListOf<Int>()

    override fun onCheckClicked(bookmarkIdx: Int, isChecked: Boolean) {
        if(isChecked) deleteBookmarkList.add(bookmarkIdx)
        else deleteBookmarkList.remove(bookmarkIdx)

        _enableEditBtn.value = deleteBookmarkList.size > 0

    }

    override fun onCompleteClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(EditBookmarkNavigationAction.NavigateToEditComplete)
        }
    }

    override fun onAllDeleteClicked() {
        baseViewModelScope.launch {
            for(index in bookmarkList.value.indices) {
                deleteBookmarkList.add(bookmarkList.value[index].bookmarkId)
            }
            _navigationHandler.emit(EditBookmarkNavigationAction.NavigateToEditComplete)
        }
    }
}