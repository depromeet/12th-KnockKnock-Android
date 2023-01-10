package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Admission
import com.depromeet.knockknock.databinding.ItemRecyclerInviteRoomBinding

class AlarmInviteRoomViewHolder(
    private val binding: ItemRecyclerInviteRoomBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Admission) {
        binding.apply {
            model = item
            executePendingBindings()
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