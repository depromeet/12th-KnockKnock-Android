package com.depromeet.knockknock.ui.alarmroomjoined

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
class AlarmRoomJoinedFragment : BaseFragment<FragmentAlarmRoomJoinedBinding, AlarmRoomJoinedViewModel>(R.layout.fragment_alarm_room_joined) {

    private val TAG = "AlarmRoomJoinedFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_joined

    override val viewModel : AlarmRoomJoinedViewModel by viewModels()
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
                    is AlarmRoomJoinedNavigationAction.NavigateToRoom -> { moveToRoom(roomId = it.roomId) }
                    is AlarmRoomJoinedNavigationAction.NavigateToAlarmRoomSearch -> navigate(AlarmRoomJoinedFragmentDirections.actionAlarmRoomJoinedFragmentToAlarmRoomSearchFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {

        val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel)
        binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter

        val test1 = AlarmRoom(
            roomType = "OPEN",
            roomId = 1,
            roomName = "친구랑",
            roomThumbnail = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            roomCategoryName = "취직",
            roomDescription = "roomIsUnpublic을 true로 했을 떄, 실제로는 검색에서는 비공개방은 나타나지 않습니다.",
            roomIsUnpublic = true,
            roomMemberCount = 10
        )

        val test2 = AlarmRoom(
            roomType = "OPEN",
            roomId = 1,
            roomName = "친구랑",
            roomThumbnail = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            roomCategoryName = "라이프스타일",
            roomDescription = "이것은 취직하고 싶어하는 사람들의 방입니다.",
            roomIsUnpublic = true,
            roomMemberCount = 20
        )

        val test3 = AlarmRoom(
            roomType = "CLOSE",
            roomId = 1,
            roomName = "홀로외침방",
            roomThumbnail = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            roomCategoryName = "취직",
            roomDescription = "이것은 취직하고 싶어하는 사람들의 방입니다.sdfsdfsdfsdfsdfsdfdsf",
            roomIsUnpublic = false,
            roomMemberCount = 10
        )

        val alarmRoomList = listOf(test1, test2, test3)
        val friendAlarmRoomList = alarmRoomList.filter {
            it.roomType == "OPEN"
        }
        val aloneAlarmRoomList = alarmRoomList.filter {
            it.roomType == "CLOSE"
        }

        alarmRoomJoinedAdapter.submitList(alarmRoomList)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomAllClicked.collectLatest {
                if(it) {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitList(alarmRoomList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomFriendClicked.collectLatest {
                if(it) {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitList(friendAlarmRoomList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomAloneClicked.collectLatest {
                if(it) {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitList(aloneAlarmRoomList)
                }
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
