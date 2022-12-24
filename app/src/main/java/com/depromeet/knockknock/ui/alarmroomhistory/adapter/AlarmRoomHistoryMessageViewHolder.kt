package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.databinding.ItemRecycleHistoryMessageBinding
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryMessage

class AlarmRoomHistoryMessageViewHolder(
    private val binding: ItemRecycleHistoryMessageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HistoryMessage) {
        binding.apply {
            model = item
            executePendingBindings()
        }
    }
}