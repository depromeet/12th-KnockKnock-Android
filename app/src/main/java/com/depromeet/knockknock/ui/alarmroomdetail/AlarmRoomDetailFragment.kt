package com.depromeet.knockknock.ui.alarmroomdetail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlarmRoomDetailFragment : BaseFragment<FragmentAlarmRoomDetailBinding, AlarmRoomDetailViewModel>(R.layout.fragment_alarm_room_detail) {

    private val TAG = "AlarmRoomDetailFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_detail
    private val navController by lazy { findNavController() }

    override val viewModel : AlarmRoomDetailViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
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
