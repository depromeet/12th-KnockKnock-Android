package com.depromeet.knockknock.ui.alarmroomjoined

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.domain.model.Group
import com.depromeet.domain.model.GroupBriefInfo
import com.depromeet.domain.model.Member
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomJoinedBinding
import com.depromeet.knockknock.ui.alarmroomjoined.adapter.AlarmRoomJoinedAdapter
import com.depromeet.knockknock.ui.alarmroomsearch.adapter.PopularCategoryAdapter
import com.depromeet.knockknock.ui.alarmroomsearch.model.AlarmRoom
import com.depromeet.knockknock.ui.bookmark.BookmarkFragmentDirections
import com.depromeet.knockknock.ui.category.model.Category
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AlarmRoomJoinedFragment :
    BaseFragment<FragmentAlarmRoomJoinedBinding, AlarmRoomJoinedViewModel>(R.layout.fragment_alarm_room_joined) {

    private val TAG = "AlarmRoomJoinedFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_joined

    override val viewModel: AlarmRoomJoinedViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is AlarmRoomJoinedNavigationAction.NavigateToRoom -> {
                        moveToRoom(roomId = it.roomId)
                    }
                    is AlarmRoomJoinedNavigationAction.NavigateToMakeRoom -> {
                        println("moving to making room")}
                }
            }
        }
    }

    override fun initAfterBinding() {

        val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel)
        binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter

        val test1 = GroupBriefInfo(
            category = com.depromeet.domain.model.Category(
                content = "취업",
                emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                id = 1
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
                content = "취업",
                emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                id = 1
            ),
            description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
            group_id = 2,
            group_type = "OPEN",
            member_count = 10,
            public_access = true,
            thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            title = "방 제목"
        )

        val test3 = GroupBriefInfo(
            category = com.depromeet.domain.model.Category(
                content = "취업",
                emoji = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
                id = 1
            ),
            description = "취업을 위한 방 어쩌구 저쩌구 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ",
            group_id = 3,
            group_type = "OPEN",
            member_count = 10,
            public_access = true,
            thumbnail_path = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            title = "방 제목"
        )

        val alarmRoomList = listOf(test1, test2, test3)
        val friendAlarmRoomList = alarmRoomList.filter {
            it.group_type == "OPEN"
        }
        val aloneAlarmRoomList = alarmRoomList.filter {
            it.group_type == "CLOSE"
        }

        alarmRoomJoinedAdapter.submitList(alarmRoomList)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomAllClicked.collectLatest {
                if (it) {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitList(alarmRoomList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomFriendClicked.collectLatest {
                if (it) {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitList(friendAlarmRoomList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomAloneClicked.collectLatest {
                if (it) {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitList(aloneAlarmRoomList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentRoomCount.collectLatest {
                if(it>0){
                    binding.layoutWhenNoRoom.visibility = View.GONE
                    binding.alarmRoomRecycler.visibility = View.VISIBLE
                }
                else{
                    binding.layoutWhenNoRoom.visibility = View.VISIBLE
                    binding.alarmRoomRecycler.visibility = View.GONE
                }
            }
        }
    }


    //  해당하는 방으로 이동하는 로직
    private fun moveToRoom(roomId: Int) {
    }

}
