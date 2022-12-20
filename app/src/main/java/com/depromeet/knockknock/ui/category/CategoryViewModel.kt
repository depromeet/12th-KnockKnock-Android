package com.depromeet.knockknock.ui.category

import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
) : BaseViewModel(), CategoryActionHandler {

    private val _saveBtnEnable: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val saveBtnEnable: StateFlow<Boolean> = _saveBtnEnable.asStateFlow()

    private val _clickCategory: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val clickCategory: StateFlow<Int> = _clickCategory.asStateFlow()

    private val _onSaveSuccess: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val onSaveSuccess: SharedFlow<Unit> = _onSaveSuccess.asSharedFlow()

    override fun onCategoryClicked(categoryId: Int) {
        baseViewModelScope.launch {
            _saveBtnEnable.emit(true)
            _clickCategory.emit(categoryId)
        }
    }

    override fun onSaveClicked() {
        baseViewModelScope.launch {
            _onSaveSuccess.emit(Unit)
        }
    }

}