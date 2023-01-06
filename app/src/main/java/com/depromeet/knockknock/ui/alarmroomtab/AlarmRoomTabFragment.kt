package com.depromeet.knockknock.ui.alarmroomtab

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomTabBinding
import com.depromeet.knockknock.ui.alarmroomexplore.AlarmRoomExploreFragment
import com.depromeet.knockknock.ui.alarmroomjoined.AlarmRoomJoinedFragment
import com.depromeet.knockknock.ui.alarmroomtab.adapter.AlarmRoomTabAdapter
import com.depromeet.knockknock.ui.alarmroomtab.bottom.BottomMakeRoom
import com.depromeet.knockknock.ui.alarmroomtab.bottom.MakeRoomType
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AlarmRoomTabFragment :
    BaseFragment<FragmentAlarmRoomTabBinding, AlarmRoomTabViewModel>(R.layout.fragment_alarm_room_tab) {

    private val TAG = "AlarmRoomSearchFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_tab

    override val viewModel: AlarmRoomTabViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private lateinit var tabLayouts: TabLayout
    private lateinit var alarmRoomTabAdapter: AlarmRoomTabAdapter
    private lateinit var viewPager: ViewPager

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewPager = binding.pager
        tabLayouts = binding.tabLayout

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is AlarmRoomTabNavigationAction.NavigateToMakeRoomBottomSheet -> {makeRoomPopUp()}
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpTabs()
        tabLayouts.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setUpTabs() {
        val adapter = AlarmRoomTabAdapter(childFragmentManager)
        adapter.addFragment(AlarmRoomJoinedFragment(), "참여중")
        adapter.addFragment(AlarmRoomExploreFragment(), "탐색")

        viewPager!!.adapter = adapter
        tabLayouts!!.setupWithViewPager(viewPager)
    }

    private fun makeRoomPopUp() {
        val dialog: BottomMakeRoom = BottomMakeRoom {
            when(it) {
                is MakeRoomType.RoomWithFriend -> {}
                is MakeRoomType.RoomAlone -> {}
            }
        }
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }

}
