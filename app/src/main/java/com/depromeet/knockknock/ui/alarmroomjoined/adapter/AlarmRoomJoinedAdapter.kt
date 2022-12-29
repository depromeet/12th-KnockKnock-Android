package com.depromeet.knockknock.ui.alarmroomjoined.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Group
import com.depromeet.domain.model.GroupBriefInfo
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderAlarmRoomBinding
import com.depromeet.knockknock.ui.alarmroomjoined.AlarmRoomJoinedActionHandler
import com.depromeet.knockknock.ui.alarmroomsearch.model.AlarmRoom

class AlarmRoomJoinedAdapter(
    private val eventListener: AlarmRoomJoinedActionHandler
) : ListAdapter<GroupBriefInfo, AlarmRoomJoinedAdapter.ViewHolder>(AlarmRoomListItemDiffCallback){

    init { setHasStableIds(true) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderAlarmRoomBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_alarm_room,
            parent,
            false
        )
        viewDataBinding.layoutMain.setOnClickListener {
            eventListener.onRoomClicked(viewDataBinding.holder!!.group_id)
            this.notifyDataSetChanged()
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderAlarmRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: GroupBriefInfo) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object AlarmRoomListItemDiffCallback : DiffUtil.ItemCallback<GroupBriefInfo>() {
        override fun areItemsTheSame(oldItem: GroupBriefInfo, newItem: GroupBriefInfo) =
            oldItem.group_id == newItem.group_id

        override fun areContentsTheSame(oldItem: GroupBriefInfo, newItem: GroupBriefInfo) =
            oldItem == newItem
    }
}