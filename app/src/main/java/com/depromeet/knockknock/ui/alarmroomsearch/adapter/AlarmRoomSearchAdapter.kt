package com.depromeet.knockknock.ui.alarmroomsearch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.GroupBriefInfo
import com.depromeet.domain.model.GroupContent
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderAlarmRoomBinding
import com.depromeet.knockknock.ui.alarmroomjoined.adapter.AlarmRoomJoinedAdapter
import com.depromeet.knockknock.ui.alarmroomsearch.AlarmRoomSearchActionHandler
import com.depromeet.knockknock.ui.alarmroomsearch.model.AlarmRoom

class AlarmRoomSearchAdapter(
    private val eventListener: AlarmRoomSearchActionHandler
) : PagingDataAdapter<GroupContent, AlarmRoomSearchAdapter.ViewHolder>(AlarmRoomListItemDiffCallback){


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
        getItem(position)?.let { holder.bind(it) }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(private val binding: HolderAlarmRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: GroupContent) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object AlarmRoomListItemDiffCallback : DiffUtil.ItemCallback<GroupContent>() {
        override fun areItemsTheSame(oldItem: GroupContent, newItem: GroupContent) =
            oldItem.group_id == newItem.group_id

        override fun areContentsTheSame(oldItem: GroupContent, newItem: GroupContent) =
            oldItem == newItem

    }


}