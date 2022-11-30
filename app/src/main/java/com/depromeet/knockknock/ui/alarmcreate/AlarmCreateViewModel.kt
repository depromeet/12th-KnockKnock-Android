package com.depromeet.knockknock.ui.alarmcreate

import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmCreateViewModel @Inject constructor(
) : BaseViewModel() {

    var editTextMessageEvent = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)

    init {
        baseViewModelScope.launch {
            editTextMessageEvent.debounce(0).collectLatest {
                onEditTextCount(it.length)
            }
        }
    }

    private fun onEditTextCount(count: Int) {
        baseViewModelScope.launch {
            editTextMessageCountEvent.value = count
        }
    }

    fun onDeleteEditTextMessageClicked() {
        editTextMessageEvent.value = ""
    }

    private val TAG = "AlarmCreateViewModel"

}