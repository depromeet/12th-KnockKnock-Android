package com.depromeet.knockknock.ui.alarmroomhistory

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Admission
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmInviteRoomAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryMessage


@BindingAdapter("alarmInviteRoomBundleAdapter")
fun RecyclerView.bindAlarmInviteRoom(itemList: List<Admission>) {
    val boundAdapter = this.adapter
    if (boundAdapter is AlarmInviteRoomAdapter) {
        boundAdapter.submitList(itemList)
    }
}

