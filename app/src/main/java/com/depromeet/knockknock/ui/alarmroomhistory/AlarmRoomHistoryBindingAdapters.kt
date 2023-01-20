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

/**
 * 예약 메시지 알림여부에 따른 동적 UI
 * **/
@BindingAdapter("bindReservationViewVisible")
fun ConstraintLayout.bindReservationViewVisible(data: String) {
    if (data == "") this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

/**
 * 입장 요청에 따른 동적 UI
 * **/
@BindingAdapter("bindAdmissionViewVisible")
fun ConstraintLayout.bindAdmissionViewVisible(data: List<Admission>) {
    if (data.isEmpty()) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

/**
 * 알림방 타입에 따른 동적 UI
 * **/
@BindingAdapter("bindViewPublicAccessVisible")
fun ImageView.bindViewPublicAccessVisible(isPublicAccess: Boolean) {
    if (isPublicAccess) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

/**
 * 알림 메시지 여부에 따른 동적 UI
 * **/
@BindingAdapter("isMessage", "isPublicAccess")
fun ImageView.bindViewMessageVisible(isMessage: Boolean, isPublicAccess: Boolean) {
    if (isPublicAccess) {
        if (isMessage) this.visibility = View.GONE
        else this.visibility = View.VISIBLE
    }
}

/**
 * 알림방에 참여했는지에 따른 동적 UI
 * **/
@BindingAdapter("viewParticipationVisible")
fun ConstraintLayout.bindViewParticipationVisible(participation: Boolean) {
    if (participation) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

/**
 * 알림방 타입과 호스트인지에 따른 동적 UI
 * **/
@BindingAdapter("groupType", "ihost")
fun ImageView.bindViewAlarmPushVisible(groupType: String, ihost: Boolean) {
    if (groupType == "OPEN" && ihost) this.visibility = View.VISIBLE
    else if (groupType == "FRIEND") this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}