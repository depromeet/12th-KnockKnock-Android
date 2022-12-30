package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderRoomFilterBinding
import com.depromeet.knockknock.databinding.ItemRecyclerCopyRoomBinding
import com.depromeet.knockknock.ui.bookmark.model.Room

class AlarmCopyRoomAdapter(
    val callback: (roomId: Int) -> Unit
) : ListAdapter<Room, AlarmCopyRoomAdapter.ViewHolder>(FilterRoomItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: ItemRecyclerCopyRoomBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recycler_copy_room,
            parent,
            false
        )
        viewDataBinding.layoutMain.setOnClickListener {
            callback.invoke(
                viewDataBinding.holder!!.roomId,
            )
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemRecyclerCopyRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Room) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FilterRoomItemDiffCallback : DiffUtil.ItemCallback<Room>() {
        override fun areItemsTheSame(oldItem: Room, newItem: Room) =
            oldItem.roomId == newItem.roomId

        override fun areContentsTheSame(oldItem: Room, newItem: Room) =
            oldItem == newItem
    }
}