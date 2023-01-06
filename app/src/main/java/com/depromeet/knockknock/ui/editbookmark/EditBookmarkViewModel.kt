package com.depromeet.knockknock.ui.editbookmark

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.depromeet.domain.model.Notification
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.adapter.createNotificationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBookmarkViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), EditBookmarkActionHandler {

    private val TAG = "EditBookmarkViewModel"

    private val _navigationHandler: MutableSharedFlow<EditBookmarkNavigationAction> = MutableSharedFlow<EditBookmarkNavigationAction>()
    val navigationHandler: SharedFlow<EditBookmarkNavigationAction> = _navigationHandler.asSharedFlow()

    private val _enableEditBtn: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val enableEditBtn: StateFlow<Boolean> = _enableEditBtn

    private val _allDeleteClicked: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val allDeleteClicked: StateFlow<Boolean> = _allDeleteClicked.asStateFlow()

    val deleteBookmarkList = mutableListOf<Int>()

    val roomClicked: MutableStateFlow<List<Int>> = MutableStateFlow<List<Int>>(emptyList())
    val periodClicked: MutableStateFlow<Int> = MutableStateFlow<Int>(0)

    var bookmarkList: Flow<PagingData<Notification>> = emptyFlow()

    fun getStroage() {
        bookmarkList = createNotificationPager(
            mainRepository = mainRepository,
            groupids = roomClicked,
            periods = periodClicked
        ).flow.cachedIn(baseViewModelScope)
    }

    fun deleteStroage() {
        baseViewModelScope.launch {
            mainRepository.deleteStroages(deleteBookmarkList)
                .onSuccess {
                    _navigationHandler.emit(EditBookmarkNavigationAction.NavigateToDeleteComplete) }
        }
    }

    override fun onCheckClicked(bookmarkIdx: Int, isChecked: Boolean) {
        if(isChecked) deleteBookmarkList.add(bookmarkIdx)
        else deleteBookmarkList.remove(bookmarkIdx)

        _enableEditBtn.value = deleteBookmarkList.size > 0
    }

    override fun onCompleteClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(EditBookmarkNavigationAction.NavigateToEditDialog)
        }
    }

    override fun onAllDeleteClicked() {
        baseViewModelScope.launch {
            getStroage()
        }
    }
}