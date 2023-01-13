package com.depromeet.knockknock.ui.alarmroomjoined

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.filter
import com.depromeet.domain.model.GroupContent
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomJoinedBinding
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.AlarmRoomAdapter
import com.depromeet.knockknock.ui.alarmroomjoined.adapter.AlarmRoomJoinedAdapter
import com.depromeet.knockknock.ui.alarmroomtab.AlarmRoomTabFragmentDirections
import com.depromeet.knockknock.ui.alarmroomtab.bottom.BottomMakeRoom
import com.depromeet.knockknock.ui.alarmroomtab.bottom.MakeRoomType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class AlarmRoomJoinedFragment :
    BaseFragment<FragmentAlarmRoomJoinedBinding, AlarmRoomJoinedViewModel>(R.layout.fragment_alarm_room_joined) {

    private val TAG = "AlarmRoomJoinedFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_joined

    override val viewModel: AlarmRoomJoinedViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val roomAdapter by lazy { AlarmRoomJoinedAdapter(viewModel, viewModel) }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getJoinedGroups()
        lifecycleScope.launchWhenStarted {
            viewModel._joinedRoomList.collectLatest {
                val alarmRoomAdapter = AlarmRoomJoinedAdapter(viewModel, viewModel)
                binding.alarmRoomRecycler.adapter = alarmRoomAdapter
                alarmRoomAdapter.submitList(it)
            }
        }
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is AlarmRoomJoinedNavigationAction.NavigateToRoom -> {
                        //toastMessage(it.roomId.toString())
                        navController.navigate(
                            AlarmRoomTabFragmentDirections.actionAlarmRoomTabFragmentToAlarmRoomHistoryFragment(
                                it.roomId
                            )
                        )
                    }
                    is AlarmRoomJoinedNavigationAction.NavigateToMakeRoom -> {
                        makeRoomPopUp()
                    }
                }
            }
        }

//        lifecycleScope.launchWhenStarted {
//            viewModel.joinedRoomList.collectLatest {
////                toastMessage("collected")
//                val alarmRoomAdapter = AlarmRoomJoinedAdapter(viewModel, viewModel)
//                binding.alarmRoomRecycler.adapter = alarmRoomAdapter
////                toastMessage(alarmRoomAdapter.snapshot().toString())
////                println("paging adpater size is ${alarmRoomAdapter.snapshot().size}")
//                alarmRoomAdapter.submitData(it)
////                println("paging adpater size is ${alarmRoomAdapter.itemCount}")
////                toastMessage(alarmRoomAdapter.itemCount.toString())
//            }
//        }

        lifecycleScope.launchWhenStarted {
            viewModel._joinedRoomList.collectLatest {
                val alarmRoomAdapter = AlarmRoomJoinedAdapter(viewModel, viewModel)
                binding.alarmRoomRecycler.adapter = alarmRoomAdapter
                alarmRoomAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomAllClicked.collectLatest {
                if (it) roomFilterByType("ALL")
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomFriendClicked.collectLatest {
                if (it) roomFilterByType("FRIEND")
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomAloneClicked.collectLatest {
                if (it) roomFilterByType("OPEN")
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentRoomCount.collectLatest {
                if (it > 0) {
                    binding.layoutWhenNoRoom.visibility = View.GONE
                    binding.alarmRoomRecycler.visibility = View.VISIBLE
                } else {
                    binding.layoutWhenNoRoom.visibility = View.VISIBLE
                    binding.alarmRoomRecycler.visibility = View.GONE
                }
            }
        }
    }

    private fun initAdapter() {
        binding.alarmRoomRecycler.adapter = roomAdapter
    }

    private fun roomFilterByType(type: String) {
        if (type != "ALL") {
//            val filteredAlarmRoomList = viewModel.roomList.filter {
//                it.category.id == categoryId
//            }

            val filteredAlarmRoomList = viewModel._joinedRoomList.map { pagingData ->
                pagingData.filter {
                    it.group_type == type
                }
            }
            lifecycleScope.launchWhenStarted {
                filteredAlarmRoomList.collectLatest {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel, viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitList(it)
                }
            }
        } else {
            lifecycleScope.launchWhenStarted {
                viewModel._joinedRoomList.collectLatest {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel, viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitList(it)
                }
            }
        }
    }


    //  해당하는 방으로 이동하는 로직
    private fun moveToRoom(roomId: Int) {
    }

    private fun makeRoomPopUp() {
        val dialog: BottomMakeRoom = BottomMakeRoom {
            when (it) {
                is MakeRoomType.RoomWithFriend -> {
                    navController.navigate(R.id.action_alarmRoomTabFragment_to_createRoomWithFriendFragment)
                }
                is MakeRoomType.RoomAlone -> {
                    navController.navigate(R.id.action_alarmRoomTabFragment_to_aloneRoomMakeCategoryFragment)
                }
            }
        }
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }

}
