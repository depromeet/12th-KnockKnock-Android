package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.ItemRecycleHistoryMessageBinding
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryMessage

class AlarmRoomHistoryMessageViewHolder(
    private val binding: ItemRecycleHistoryMessageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HistoryMessage) {
        binding.apply {
            model = item
            executePendingBindings()
            binding.expandBtn.setOnClickListener {
                binding.model!!.isExpanded = !(binding.model!!.isExpanded)

                if (binding.model!!.isExpanded) {
                    binding.expandBtn.apply {
                        this.text = context.getString(R.string.shorts_contents)
                    }
                    binding.contents.maxLines = 9999
                } else {
                    binding.expandBtn.apply {
                        this.text = context.getString(R.string.more_contents)
                    }
                    binding.contents.maxLines = 2
                }
            }
        }
    }
}