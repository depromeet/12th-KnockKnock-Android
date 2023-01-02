package com.depromeet.knockknock.ui.alarmroomhistory

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Admission
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmInviteRoomAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmRoomHistoryBundleAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmRoomHistoryMessageAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryBundle
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryMessage
import com.depromeet.knockknock.ui.alarmroomhistory.model.InviteRoom


@BindingAdapter("alarmInviteRoomBundleAdapter")
fun RecyclerView.bindAlarmInviteRoom(itemList: List<Admission>) {
    val boundAdapter = this.adapter
    if (boundAdapter is AlarmInviteRoomAdapter) {
        boundAdapter.submitList(itemList)
    }
}

@BindingAdapter("alarmRoomHistoryBundleAdapter")
fun RecyclerView.bindAlarmRoomHistoryBundle(itemList: List<HistoryBundle>) {
    val boundAdapter = this.adapter
    if (boundAdapter is AlarmRoomHistoryBundleAdapter) {
        boundAdapter.submitList(itemList)
    }
}

@BindingAdapter("alarmRoomHistoryMessageAdapter")
fun RecyclerView.bindAlarmRoomHistoryMessage(itemList: List<HistoryMessage>) {
    val boundAdapter = this.adapter
    if (boundAdapter is AlarmRoomHistoryMessageAdapter) {
        boundAdapter.submitList(itemList)
    }
}
