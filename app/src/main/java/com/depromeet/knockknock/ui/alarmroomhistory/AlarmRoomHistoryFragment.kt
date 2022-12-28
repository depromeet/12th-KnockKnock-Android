package com.depromeet.knockknock.ui.alarmroomhistory

import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomHistoryBinding
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmInviteRoomAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmRoomHistoryBundleAdapter
import com.depromeet.knockknock.ui.home.bottom.AlarmMoreType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AlarmRoomHistoryFragment :
    BaseFragment<FragmentAlarmRoomHistoryBinding, AlarmRoomHistoryViewModel>(R.layout.fragment_alarm_room_history) {

    private val TAG = "AlarmRoomHistoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_history
    private val navController by lazy { findNavController() }

    override val viewModel: AlarmRoomHistoryViewModel by viewModels()
    private val alarmRoomHistoryBundleAdapter by lazy {
        AlarmRoomHistoryBundleAdapter(
            viewModel,
            viewModel
        )
    }
    private val alarmInviteRoomAdapter by lazy { AlarmInviteRoomAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
        setupEvent()
        binding.apply {

            // 방 설명 더보기/접기
            isEllipsis(previewMessageTextView)
            expandBtn.setOnClickListener {
                previewMessageTextView.maxLines = 9999
                expandBtn.visibility = View.GONE
                closeBtn.visibility = View.VISIBLE
            }

            closeBtn.setOnClickListener {
                previewMessageTextView.maxLines = 1
                closeBtn.visibility = View.GONE
                expandBtn.visibility = View.VISIBLE
            }

            // 예약 알림 더보기/접기
            ivItemReservationUp.setOnClickListener {
                if (tvItemReservationContent.maxLines == 2) {
                    tvItemReservationContent.maxLines = 9999
                    ivItemReservationUp.animate().setDuration(200).rotation(180f)
                    reservationBtnLayout.visibility = View.VISIBLE
                    contentsImg.visibility = View.VISIBLE

                } else {
                    tvItemReservationContent.maxLines = 2
                    ivItemReservationUp.animate().setDuration(200).rotation(0f)
                    reservationBtnLayout.visibility = View.GONE
                    contentsImg.visibility = View.GONE
                }
            }

            ivInviteFold.setOnClickListener {
                if (rvInviteList.visibility == View.GONE) {
                    rvInviteList.visibility = View.VISIBLE
                    ivInviteFold.animate().setDuration(200).rotation(0f)
                } else {
                    rvInviteList.visibility = View.GONE
                    ivInviteFold.animate().setDuration(200).rotation(180f)
                }
            }
        }
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is AlarmRoomHistoryNavigationAction.NavigateToAlarmMore -> initAlarmMoreBottomSheet(
                        roomId = it.roomId
                    )

                }
            }
        }
    }

    private fun initAlarmMoreBottomSheet(roomId: Int) {
        val dialog = com.depromeet.knockknock.ui.home.bottom.BottomAlarmMore {
            when (it) {
                is AlarmMoreType.Copy -> {}
                is AlarmMoreType.Save -> {}
                is AlarmMoreType.Delete -> {}
                is AlarmMoreType.Declare -> {}
                is AlarmMoreType.Report -> {}
            }
        }
        dialog.show(childFragmentManager, TAG)
    }


    // 1이 나온다는 것은 글씨가 줄여졌다는 것이다.
    private fun isEllipsis(textView: TextView) {
        textView.post {
            if (textView.layout.lineCount > 0) {
                if (textView.layout.getEllipsisCount(textView.layout.lineCount - 1) > 0) {
                    binding.expandBtn.visibility = View.VISIBLE
                } else {
                    binding.expandBtn.visibility = View.GONE
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvList.adapter = alarmRoomHistoryBundleAdapter
        binding.rvInviteList.adapter = alarmInviteRoomAdapter

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
