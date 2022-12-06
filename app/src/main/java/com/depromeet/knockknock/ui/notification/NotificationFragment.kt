package com.depromeet.knockknock.ui.notification

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomBinding
import com.depromeet.knockknock.databinding.FragmentNotificationBinding
import com.depromeet.knockknock.ui.alarmroom.AlarmRoomViewModel
import com.depromeet.knockknock.ui.notification.adapter.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(R.layout.fragment_notification) {

    private val TAG = "NotificationFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_notification

    override val viewModel : NotificationViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.notification_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.friendRecycler.adapter = NotificationAdapter(viewModel)
    }
}
