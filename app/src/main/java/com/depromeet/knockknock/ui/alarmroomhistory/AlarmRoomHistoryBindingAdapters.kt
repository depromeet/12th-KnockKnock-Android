package com.depromeet.knockknock.ui.alarmroomhistory

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmRoomHistoryBundleAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmRoomHistoryMessageAdapter
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryBundle
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryMessage


@BindingAdapter("alarmRoomHistoryBundleAdapter")
fun RecyclerView.bindAlarmRoomHistoryBundle(itemList: List<HistoryBundle>) {
    val boundAdapter = this.adapter
    if (boundAdapter is AlarmRoomHistoryBundleAdapter) {
        boundAdapter.submitList(itemList)
    }
}

@BindingAdapter("alarmRoomHistoryMessageAdapter")
fun RecyclerView.bindAlarmRoomHistoryMessage(itemList: List<HistoryMessage>) {
    Log.d("ttt", itemList.toString())
    val boundAdapter = this.adapter
    if (boundAdapter is AlarmRoomHistoryMessageAdapter) {
        boundAdapter.submitList(itemList)
    }
}
