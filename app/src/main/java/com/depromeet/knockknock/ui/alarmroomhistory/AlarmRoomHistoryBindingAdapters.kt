package com.depromeet.knockknock.ui.alarmroomhistory

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Admission
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmInviteRoomAdapter


@BindingAdapter("alarmInviteRoomBundleAdapter")
fun RecyclerView.bindAlarmInviteRoom(itemList: List<Admission>) {
    val boundAdapter = this.adapter
    if (boundAdapter is AlarmInviteRoomAdapter) {
        boundAdapter.submitList(itemList)
    }
}

@BindingAdapter("bindViewVisible")
fun ConstraintLayout.bindViewVisible(data: String) {
    if (data == "") this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

@BindingAdapter("bindViewVisible")
fun ConstraintLayout.bindViewVisible(data: List<Admission>) {
    if (data.isEmpty()) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

@BindingAdapter("bindViewVisible")
fun ImageView.bindViewVisible(isPublicAccess: Boolean) {
    if (isPublicAccess) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

@BindingAdapter("isMessage", "isPublicAccess")
fun ImageView.bindViewMessageVisible(isMessage: Boolean, isPublicAccess: Boolean) {
    if (isPublicAccess) {
        if (isMessage) this.visibility = View.GONE
        else this.visibility = View.VISIBLE
    }
}

@BindingAdapter("viewParticipationVisible")
fun ConstraintLayout.bindViewParticipationVisible(participation: Boolean) {
    if (participation) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter("groupType", "ihost")
fun ImageView.bindViewAlarmPushVisible(groupType: String, ihost: Boolean) {
    if (groupType == "OPEN" && ihost) this.visibility = View.VISIBLE
    else if (groupType == "FRIEND") this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}