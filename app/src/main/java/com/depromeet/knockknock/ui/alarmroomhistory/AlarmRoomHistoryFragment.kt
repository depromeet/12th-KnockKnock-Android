package com.depromeet.knockknock.ui.alarmroomhistory

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.map
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultRedAlertDialog
import com.depromeet.knockknock.databinding.FragmentAlarmRoomHistoryBinding
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmInviteRoomAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmRoomHistoryMessageAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.bottom.BottomAlarmCopyRoom
import com.depromeet.knockknock.ui.alarmroomhistory.bottom.BottomAlarmReport
import com.depromeet.knockknock.ui.aloneroominvitefriend.AloneRoomInviteFriendFragmentArgs
import com.depromeet.knockknock.ui.bookmark.model.Room
import com.depromeet.knockknock.ui.home.bottom.AlarmMoreType
import com.depromeet.knockknock.ui.home.bottom.BottomAlarmMore
import com.depromeet.knockknock.util.defaultreaction.DefaultReactionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AlarmRoomHistoryFragment :
    BaseFragment<FragmentAlarmRoomHistoryBinding, AlarmRoomHistoryViewModel>(R.layout.fragment_alarm_room_history) {

    private val TAG = "AlarmRoomHistoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_history
    private val navController by lazy { findNavController() }
    private val args: AlarmRoomHistoryFragmentArgs by navArgs()

    override val viewModel: AlarmRoomHistoryViewModel by viewModels()
    private val alarmRoomHistoryMessageAdapter by lazy {
        AlarmRoomHistoryMessageAdapter(
            viewModel,
            viewModel
        )
    }
    private val alarmInviteRoomAdapter by lazy { AlarmInviteRoomAdapter(viewModel, viewModel) }

    override fun initStartView() {
        viewModel.groupId.value = args.groupId
        viewModel.getGroupAdmissions()
        viewModel.getPushAlarm()
        viewModel.getGroups()

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
                        alarmId = it.alarmId, message = it.message
                    )
                    is AlarmRoomHistoryNavigationAction.NavigateToAlarmCreate -> navigate(
                        AlarmRoomHistoryFragmentDirections.actionAlarmRoomHistoryFragmentToAlarmCreateFragment(
                            it.roomId,
                            it.roomTitle,
                            it.title,
                            it.copyMessage,
                            it.reservation
                        )
                    )
                    is AlarmRoomHistoryNavigationAction.NavigateToReaction -> reactionBottomSheet(
                        notification_id = it.notification_id,
                        reaction_id = it.reaction_id
                    )
                    is AlarmRoomHistoryNavigationAction.NavigateToBookmarkFilterReset -> {}
                    is AlarmRoomHistoryNavigationAction.NavigateToSettingRoomForUser -> {
                        navigate(
                            AlarmRoomHistoryFragmentDirections.actionAlarmRoomHistoryFragmentToAlarmSettingFragment2()
                        )
                    }
                    is AlarmRoomHistoryNavigationAction.NavigateToSettingRoom -> {
                        navigate(
                            AlarmRoomHistoryFragmentDirections.actionAlarmRoomHistoryFragmentToSettingRoomFragment(
                                it.alarmId
                            )
                        )
                    }
                }
            }
        }
    }

    private fun reactionBottomSheet(notification_id: Int, reaction_id: Int) {
        val bottomSheet = DefaultReactionDialog(reaction_id) {
            viewModel.stroageReaction(reaction_id = it, notification_id = notification_id)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun initAlarmMoreBottomSheet(alarmId: Int, message: String) {
        val dialog: BottomAlarmMore = BottomAlarmMore {
            when (it) {
                is AlarmMoreType.Copy -> roomFilter(message)
                is AlarmMoreType.Save -> {
                    viewModel.onAlarmSaveClicked(alarmId)
                }
                is AlarmMoreType.Delete -> alarmDeleteDialog(alarmId)
                is AlarmMoreType.Declare -> usersBlockDialog()
                is AlarmMoreType.Report -> reportDialog(alarmId)
            }
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun alarmDeleteDialog(notificationId: Int) {
        val res = AlertDialogModel(
            title = "이 알림을 삭제할까요?",
            description = "내 알림방에서만 볼 수 없어요",
            positiveContents = "삭제하기",
            negativeContents = getString(R.string.no)
        )
        val dialog: DefaultRedAlertDialog = DefaultRedAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                toastMessage("알림 삭제")
                viewModel.onDeleteAlarmClicked(notificationId)
            },
            clickToNegative = {
                toastMessage("아니요")
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun usersBlockDialog() {
        val res = AlertDialogModel(
            title = "이 유저를 차단할까요?",
            description = "앞으로 이 유저의 글을 볼 수 없어요",
            positiveContents = "차단하기",
            negativeContents = getString(R.string.no)
        )
        val dialog: DefaultRedAlertDialog = DefaultRedAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                toastMessage("유저 차단")
            },
            clickToNegative = {
                toastMessage("아니요")
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun roomFilter(copyMessage: String) {
        val bottomSheet = BottomAlarmCopyRoom { clickedRoom ->
            viewModel.onAlarmCreateClicked(clickedRoom, "", copyMessage, 0)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun reportDialog(alarmId : Int) {
        val bottomSheet = BottomAlarmReport(
        ) { reportReason ->
            viewModel.onReportClicked(alarmId, reportReason)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
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
        lifecycleScope.launchWhenStarted {
            viewModel.pushAlarmList.collectLatest {
                alarmRoomHistoryMessageAdapter.submitData(it)
            }
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {
        binding.rvInviteList.adapter = alarmInviteRoomAdapter
        binding.rvList.adapter = alarmRoomHistoryMessageAdapter

    }

    override fun onResume() {
        viewModel.getPushAlarm()
        super.onResume()
    }
}
