package com.depromeet.knockknock.ui.alarmroomhistory

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultRedAlertDialog
import com.depromeet.knockknock.databinding.DialogRedDefaultAlertBinding
import com.depromeet.knockknock.databinding.FragmentAlarmRoomHistoryBinding
import com.depromeet.knockknock.ui.alarmcreate.AlarmCreateFragmentDirections
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmInviteRoomAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmRoomHistoryBundleAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.bottom.BottomAlarmCopyRoom
import com.depromeet.knockknock.ui.alarmroomhistory.bottom.BottomAlarmReport
import com.depromeet.knockknock.ui.bookmark.bottom.BottomPeriodFilter
import com.depromeet.knockknock.ui.bookmark.bottom.BottomRoomFilter
import com.depromeet.knockknock.ui.bookmark.model.Room
import com.depromeet.knockknock.ui.home.bottom.AlarmMoreType
import com.depromeet.knockknock.ui.home.bottom.BottomAlarmMore
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

            reservationDeleteBtn.setOnClickListener {
//                viewModel.onAlarmCreateClicked(clickedRoom, copyMessage, true)
            }
        }
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is AlarmRoomHistoryNavigationAction.NavigateToAlarmMore -> initAlarmMoreBottomSheet(
                        roomId = it.roomId, message = it.message)
                    is AlarmRoomHistoryNavigationAction.NavigateToAlarmCreate -> navigate(
                        AlarmRoomHistoryFragmentDirections.actionAlarmRoomHistoryFragmentToAlarmCreateFragment(it.roomId, it.copyMessage, it.reservation)
                    )
                }
            }
        }
    }

    private fun initAlarmMoreBottomSheet(roomId: Int, message: String) {
        val dialog : BottomAlarmMore = BottomAlarmMore {
            when (it) {
                is AlarmMoreType.Copy -> roomFilter(message)
                is AlarmMoreType.Save -> {}
                is AlarmMoreType.Delete -> alarmDeleteDialog()
                is AlarmMoreType.Declare -> usersBlockDialog()
                is AlarmMoreType.Report -> periodFilter()
            }
        }
        dialog.show(childFragmentManager, TAG)
    }
    private fun alarmDeleteDialog() {
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
        val test1 = Room(
            roomId = 1,
            roomName = "테스트 1호방",
            roomImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            isChecked = false
        )
        val test2 = Room(
            roomId = 2,
            roomName = "테스트 2호방",
            roomImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            isChecked = false
        )
        val test3 = Room(
            roomId = 3,
            roomName = "테스트 3호방",
            roomImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            isChecked = false
        )
        val test4 = Room(
            roomId = 4,
            roomName = "테스트 4호방",
            roomImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            isChecked = false
        )
        val testList = listOf(test1, test2, test3, test4)

        val bottomSheet = BottomAlarmCopyRoom(
            roomList = testList,
        ) { clickedRoom ->
            viewModel.onAlarmCreateClicked(clickedRoom, copyMessage, false)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun periodFilter() {
        val bottomSheet = BottomAlarmReport(
            period = viewModel.periodClicked.value
        ) { clickedPeriod ->
            toastMessage("$clickedPeriod 선택함")
//            viewModel.setPeriodFilter(clickedPeriod)
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
