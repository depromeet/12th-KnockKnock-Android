package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.databinding.ItemRecycleHistoryBundleBinding
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryBundle

class AlarmRoomHistoryBundleViewHolder(
    private val binding: ItemRecycleHistoryBundleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: HistoryBundle
    ) {
        binding.apply {
            model = item
            executePendingBindings()
        }
    }
}