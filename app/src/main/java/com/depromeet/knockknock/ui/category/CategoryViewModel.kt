package com.depromeet.knockknock.ui.category

import com.depromeet.domain.model.Category
import com.depromeet.domain.model.Group
import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.model.Member
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), CategoryActionHandler {

    private val _saveBtnEnable: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val saveBtnEnable: StateFlow<Boolean> = _saveBtnEnable.asStateFlow()

    private val _clickCategory: MutableStateFlow<Int> = MutableStateFlow<Int>(1)
    val clickCategory: StateFlow<Int> = _clickCategory.asStateFlow()

    private val _onSaveSuccess: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val onSaveSuccess: SharedFlow<Unit> = _onSaveSuccess.asSharedFlow()

    var group_id = MutableStateFlow<Int>(1)
    var group_title = MutableStateFlow<String>("방 이름")
    var group_description = MutableStateFlow<String>("방 설명")
    var group_thumbnail_path = MutableStateFlow<String>("https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23")
    var group_background_path = MutableStateFlow<String>("https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23")
    var group_category_id = MutableStateFlow<Int>(0)
    var group_public_access = MutableStateFlow<Boolean>(false)

//    var group = MutableStateFlow<Group>(
//        Group(
//            background_image_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//            category = Category(
//                id = 2,
//                content = "스터디",
//                emoji = "1"
//            ),
//            description = "무슨무슨 방입니다",
//            group_id = 17,
//            group_type = "OPEN",
//            ihost = true,
//            members = listOf(
//                Member(
//                    nick_name = "이영준",
//                    profile_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//                    user_id = 1
//                )
//            ),
//            public_access = true,
//            thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
//            title = "방 이름"
//        )
//    )

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



    override fun onCategoryClicked(categoryId: Int) {
        baseViewModelScope.launch {
            _saveBtnEnable.emit(true)
            group_category_id.emit(categoryId)
        }
    }

    override fun onSaveClicked() {
        baseViewModelScope.launch {
            mainRepository.putGroup(
                id = group_id.value,
                title = group_title.value,
                description = group_description.value,
                public_access = group_public_access.value,
                thumbnail_path = group_thumbnail_path.value,
                background_image_path = group_background_path.value,
                category_id = group_category_id.value
            ,)
            _onSaveSuccess.emit(Unit)
        }
    }

}