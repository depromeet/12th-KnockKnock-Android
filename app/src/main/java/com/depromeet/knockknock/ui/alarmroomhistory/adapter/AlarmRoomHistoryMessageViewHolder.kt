package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.ItemRecyclerHistoryMessageBinding
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryMessage

class AlarmRoomHistoryMessageViewHolder(
    private val binding: ItemRecyclerHistoryMessageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HistoryMessage) {
        binding.apply {
            model = item
            executePendingBindings()
            expandBtn.setOnClickListener {
                model!!.isExpanded = !(model!!.isExpanded)

                if (model!!.isExpanded) {
                    expandBtn.apply {
                        this.text = context.getString(R.string.shorts_contents)
                    }
                    contents.maxLines = 9999
                } else {
                    expandBtn.apply {
                        this.text = context.getString(R.string.more_contents)
                    }
                    contents.maxLines = 2
                }
            }
        }
    }
}