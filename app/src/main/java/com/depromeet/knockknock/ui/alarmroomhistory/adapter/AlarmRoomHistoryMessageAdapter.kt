package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.knockknock.databinding.ItemRecycleHistoryMessageBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryActionHandler
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryMessage

class AlarmRoomHistoryMessageAdapter(
    private val eventListener: AlarmRoomHistoryActionHandler,
) : ListAdapter<HistoryMessage, AlarmRoomHistoryMessageViewHolder>(AlarmRoomHistoryMessageItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmRoomHistoryMessageViewHolder {
        return AlarmRoomHistoryMessageViewHolder(
            ItemRecycleHistoryMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                eventListener = this@AlarmRoomHistoryMessageAdapter.eventListener
            }
        )
    }

    override fun onBindViewHolder(holder: AlarmRoomHistoryMessageViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    internal object AlarmRoomHistoryMessageItemDiffCallback : DiffUtil.ItemCallback<HistoryMessage>() {
        override fun areItemsTheSame(oldItem: HistoryMessage, newItem: HistoryMessage) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HistoryMessage, newItem: HistoryMessage) =
            oldItem.equals(newItem)
    }
}