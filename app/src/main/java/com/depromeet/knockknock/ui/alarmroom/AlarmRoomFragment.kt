package com.depromeet.knockknock.ui.alarmroom

import androidx.fragment.app.viewModels
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlarmRoomFragment : BaseFragment<FragmentAlarmRoomBinding, AlarmRoomViewModel>(R.layout.fragment_alarm_room) {

    private val TAG = "AlarmRoomFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room

    override val viewModel : AlarmRoomViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
