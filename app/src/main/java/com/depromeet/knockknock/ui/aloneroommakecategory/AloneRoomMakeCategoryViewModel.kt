package com.depromeet.knockknock.ui.aloneroommakecategory

import com.depromeet.domain.model.Category
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.settingroom.SettingRoomNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AloneRoomMakeCategoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), AloneRoomMakeCategoryActionHandler {

    private val _navigationHandler: MutableSharedFlow<AloneRoomMakeCategoryNavigationAction> =
        MutableSharedFlow<AloneRoomMakeCategoryNavigationAction>()
    val navigationHandler: SharedFlow<AloneRoomMakeCategoryNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _saveBtnEnable: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val saveBtnEnable: StateFlow<Boolean> = _saveBtnEnable.asStateFlow()

    private val _clickCategory: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)
    val clickCategory: StateFlow<Int?> = _clickCategory.asStateFlow()

    private val _categoryList: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList.asStateFlow()

    init{
        baseViewModelScope.launch {
            mainRepository.getGroupCategories()
                .onSuccess {
                    _categoryList.emit(it.categories)
                }
        }
    }

    fun getCategories(){
        baseViewModelScope.launch {
            mainRepository.getGroupCategories()
                .onSuccess {
                    _categoryList.emit(it.categories)
                }
        }
    }

    override fun onCategoryClicked(categoryId: Int) {
        baseViewModelScope.launch {
            _clickCategory.value = categoryId
            _saveBtnEnable.emit(true)
        }
    }

    override fun onNextClicked(){
        baseViewModelScope.launch {
            _navigationHandler.emit(AloneRoomMakeCategoryNavigationAction.NavigateToAloneRoomDetails(categoryId = _clickCategory.value))
        }
    }

    override fun onSkipClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AloneRoomMakeCategoryNavigationAction.NavigateToAloneRoomDetails(categoryId = 13))
        }
    }
}