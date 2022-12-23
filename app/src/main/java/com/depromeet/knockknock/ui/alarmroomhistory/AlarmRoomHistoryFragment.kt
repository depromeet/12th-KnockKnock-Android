package com.depromeet.knockknock.ui.alarmroomhistory

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomHistoryBinding
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmRoomHistoryBundleAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmRoomHistoryMessageAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlarmRoomHistoryFragment : BaseFragment<FragmentAlarmRoomHistoryBinding, AlarmRoomHistoryViewModel>(R.layout.fragment_alarm_room_history) {

    private val TAG = "AlarmRoomHistoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_history
    private val navController by lazy { findNavController() }

    override val viewModel : AlarmRoomHistoryViewModel by viewModels()
    private val alarmRoomHistoryBundleAdapter by lazy { AlarmRoomHistoryBundleAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvList.adapter = alarmRoomHistoryBundleAdapter
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
