package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.knockknock.databinding.ItemRecycleHistoryBundleBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryActionHandler
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryBundle

class AlarmRoomHistoryBundleAdapter(
    private val eventListener: AlarmRoomHistoryActionHandler,
) : ListAdapter<HistoryBundle, AlarmRoomHistoryBundleViewHolder>(AlarmRoomHistoryBundleItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmRoomHistoryBundleViewHolder {
        return AlarmRoomHistoryBundleViewHolder(
            ItemRecycleHistoryBundleBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                eventListener = this@AlarmRoomHistoryBundleAdapter.eventListener
            }
        )
    }

    override fun onBindViewHolder(holder: AlarmRoomHistoryBundleViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    internal object AlarmRoomHistoryBundleItemDiffCallback : DiffUtil.ItemCallback<HistoryBundle>() {
        override fun areItemsTheSame(oldItem: HistoryBundle, newItem: HistoryBundle) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HistoryBundle, newItem: HistoryBundle) =
            oldItem.equals(newItem)
    }
}