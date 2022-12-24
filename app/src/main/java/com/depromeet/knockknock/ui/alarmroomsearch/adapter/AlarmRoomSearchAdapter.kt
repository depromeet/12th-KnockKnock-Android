package com.depromeet.knockknock.ui.alarmroomsearch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderAlarmRoomBinding
import com.depromeet.knockknock.ui.alarmroomsearch.AlarmRoomSearchActionHandler
import com.depromeet.knockknock.ui.alarmroomsearch.model.AlarmRoom

class AlarmRoomSearchAdapter(
    private val eventListener: AlarmRoomSearchActionHandler
) : ListAdapter<AlarmRoom, AlarmRoomSearchAdapter.ViewHolder>(AlarmRoomListItemDiffCallback){

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
            eventListener.onRoomClicked(viewDataBinding.holder!!.roomId)
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
        fun bind(item: AlarmRoom) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object AlarmRoomListItemDiffCallback : DiffUtil.ItemCallback<AlarmRoom>() {
        override fun areItemsTheSame(oldItem: AlarmRoom, newItem: AlarmRoom) =
            oldItem.roomId == newItem.roomId

        override fun areContentsTheSame(oldItem: AlarmRoom, newItem: AlarmRoom) =
            oldItem == newItem
    }
}