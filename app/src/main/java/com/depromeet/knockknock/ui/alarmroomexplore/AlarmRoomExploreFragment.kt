package com.depromeet.knockknock.ui.alarmroomexplore

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.domain.model.Category
import com.depromeet.domain.model.GroupBriefInfo
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomExploreBinding
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.CategoryAdapter
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.PopularRoomAdapter
import com.depromeet.knockknock.ui.alarmroomjoined.adapter.AlarmRoomJoinedAdapter
import com.depromeet.knockknock.ui.alarmroomsearch.adapter.AlarmRoomAdapter
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AlarmRoomExploreFragment : BaseFragment<FragmentAlarmRoomExploreBinding, AlarmRoomExploreViewModel>(R.layout.fragment_alarm_room_explore) {

    private val TAG = "AlarmRoomSearchFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_explore

    override val viewModel : AlarmRoomExploreViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initEditText()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is AlarmRoomExploreNavigationAction.NavigateToRoom -> { moveToRoom(roomId = it.roomId) }
                    is AlarmRoomExploreNavigationAction.NavigateToAlarmRoomSearch -> {navigate(AlarmRoomExploreFragmentDirections.actionAlarmRoomExploreFragmentToAlarmRoomSearchFragment())}
                    is AlarmRoomExploreNavigationAction.NavigateToMakeRoom -> {}
                }
            }
        }
    }

    override fun initAfterBinding() {

        val alarmRoomAdapter = AlarmRoomAdapter(viewModel)
        binding.alarmRoomRecycler.adapter = alarmRoomAdapter

        val test1 = GroupBriefInfo(
        category = com.depromeet.domain.model.Category(
            content = "취업",
            emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            id = 2
        ),
        description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
        group_id = 1,
        group_type = "OPEN",
        member_count = 10,
        public_access = true,
        thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
        title = "방 제목"
        )

        val test2 = GroupBriefInfo(
            category = com.depromeet.domain.model.Category(
                content = "스터디",
                emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                id = 3
            ),
            description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
            group_id = 1,
            group_type = "OPEN",
            member_count = 10,
            public_access = true,
            thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            title = "방 제목"
        )
        val test3 = GroupBriefInfo(
            category = Category(
                content = "독서",
                emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                id = 4
            ),
            description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
            group_id = 1,
            group_type = "OPEN",
            member_count = 10,
            public_access = true,
            thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            title = "방 제목"
        )

        val alarmRoomList = listOf(test1, test2, test3)
        alarmRoomAdapter.submitList(alarmRoomList)



        val categorySelectAdapter = CategoryAdapter(viewModel)
        binding.categorySelectRecycler.adapter = categorySelectAdapter
        val categoryTest1 = Category(
            id = 1,
            emoji = "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/apple/325/eyes_1f440.png",
            content = "전체"
        )

        val categoryTest2 = Category(
            id = 2,
            emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            content = "취업"
        )

        val categoryTest3 = Category(
            id = 3,
            emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            content = "스터디"
        )

        val categoryTest4 = Category(
            id = 4,
            emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            content = "독서"
        )

        val categoryTest5 = Category(
            id = 5,
            emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            content = "5번째 카테고리"
        )

        val categoryList = listOf(categoryTest1, categoryTest2, categoryTest3, categoryTest4, categoryTest5)
        categorySelectAdapter.submitList(categoryList)


        val popularRoomAdapter = PopularRoomAdapter(viewModel)
        binding.popularRoomRecycler.adapter = popularRoomAdapter

        val popularRoomTest1 = GroupBriefInfo(
            category = Category(
                content = "취업",
                emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                id = 2
            ),
            description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
            group_id = 1,
            group_type = "OPEN",
            member_count = 10,
            public_access = true,
            thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            title = "방 제목"
        )

        val popularRoomTest2 = GroupBriefInfo(
            category = com.depromeet.domain.model.Category(
                content = "스터디",
                emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                id = 3
            ),
            description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
            group_id = 1,
            group_type = "OPEN",
            member_count = 10,
            public_access = true,
            thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            title = "방 제목"
        )
        val popularRoomTest3 = GroupBriefInfo(
            category = Category(
                content = "독서",
                emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                id = 4
            ),
            description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
            group_id = 1,
            group_type = "OPEN",
            member_count = 10,
            public_access = true,
            thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            title = "방 제목"
        )

        val popularRoomList = listOf(test1, test2, test3)
        popularRoomAdapter.submitList(popularRoomList)

        fun roomFilterByCategory(categoryId : Int) {
            if (categoryId != 1) {
                val filteredAlarmRoomList = alarmRoomList.filter {
                    it.category.id == categoryId
                }
                val alarmRoomAdapter = AlarmRoomAdapter(viewModel)
                binding.alarmRoomRecycler.adapter = alarmRoomAdapter
                alarmRoomAdapter.submitList(filteredAlarmRoomList)
            }

            else{
                val alarmRoomAdapter = AlarmRoomAdapter(viewModel)
                binding.alarmRoomRecycler.adapter = alarmRoomAdapter
                alarmRoomAdapter.submitList(alarmRoomList)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.clickedCategoryName.collectLatest {
                roomFilterByCategory(it)
            }
        }
    }



//  해당하는 방으로 이동하는 로직
    private fun moveToRoom(roomId: Int) {

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        binding.searchEditText.customOnFocusChangeListener(requireContext())
        binding.layoutMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.searchEditText.clearFocus()
            false
        }
    }



}
