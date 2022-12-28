package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.knockknock.databinding.ItemRecyclerHistoryBundleBinding
import com.depromeet.knockknock.databinding.ItemRecyclerInviteRoomBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryActionHandler
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryBundle
import com.depromeet.knockknock.ui.alarmroomhistory.model.InviteRoom

class AlarmInviteRoomAdapter(
    private val eventListener: AlarmRoomHistoryActionHandler
    ) : ListAdapter<InviteRoom, AlarmInviteRoomViewHolder>(AlarmInviteRoomItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmInviteRoomViewHolder {
        return AlarmInviteRoomViewHolder(
            ItemRecyclerInviteRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                eventListener = this@AlarmInviteRoomAdapter.eventListener
            }
        )
    }

    override fun onBindViewHolder(holder: AlarmInviteRoomViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)

        }
    }

    internal object AlarmInviteRoomItemDiffCallback : DiffUtil.ItemCallback<InviteRoom>() {
        override fun areItemsTheSame(oldItem: InviteRoom, newItem: InviteRoom) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: InviteRoom, newItem: InviteRoom) =
            oldItem.equals(newItem)
    }
}