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
import com.depromeet.knockknock.ui.alarmroomjoined.adapter.AlarmRoomJoinedAdapter
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
    private val roomAdapter by lazy { AlarmRoomJoinedAdapter(viewModel,viewModel) }
    private lateinit var alarmRoomList : PagingData<GroupContent>
    private lateinit var friendAlarmRoomList : PagingData<GroupContent>
    private lateinit var aloneAlarmRoomList : PagingData<GroupContent>

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
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

        lifecycleScope.launchWhenStarted {
            viewModel.joinedRoomList.collectLatest {
                alarmRoomList = it
                friendAlarmRoomList = alarmRoomList.filter {
                    it.group_type == "OPEN"
                }
                aloneAlarmRoomList = alarmRoomList.filter {
                    it.group_type == "CLOSE"
                }
                roomAdapter.submitData(it)
            }
        }
    }

    override fun initAfterBinding() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomAllClicked.collectLatest {
                if (it) {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel,viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitData(alarmRoomList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomFriendClicked.collectLatest {
                if (it) {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel,viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitData(friendAlarmRoomList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.roomAloneClicked.collectLatest {
                if (it) {
                    val alarmRoomJoinedAdapter = AlarmRoomJoinedAdapter(viewModel,viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomJoinedAdapter
                    alarmRoomJoinedAdapter.submitData(aloneAlarmRoomList)
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

    private fun initAdapter() {
        binding.alarmRoomRecycler.adapter = roomAdapter
    }


    //  해당하는 방으로 이동하는 로직
    private fun moveToRoom(roomId: Int) {
    }

}
