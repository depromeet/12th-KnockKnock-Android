package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Admission
import com.depromeet.domain.model.Notification
import com.depromeet.knockknock.databinding.ItemRecyclerInviteRoomBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel

class AlarmInviteRoomViewHolder(
    private val binding: ItemRecyclerInviteRoomBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Admission,
        viewModel: AlarmRoomHistoryViewModel,
        date: String
    ) {
        binding.apply {
            model = item
            executePendingBindings()
            vm = viewModel
            viewModel.alarmInviteDateEvent.value = date
//            ivFold.setOnClickListener {
//                if (recyclerView.visibility == View.VISIBLE) {
//                    recyclerView.visibility = View.GONE
//                    ivFold.animate().setDuration(200).rotation(0f)
//                } else {
//                    recyclerView.visibility = View.VISIBLE
//                    ivFold.animate().setDuration(200).rotation(180f)
//                }
//            }
        }
    }
}