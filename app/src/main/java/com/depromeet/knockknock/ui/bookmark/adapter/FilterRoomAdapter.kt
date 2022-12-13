package com.depromeet.knockknock.ui.bookmark.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderRoomFilterBinding
import com.depromeet.knockknock.ui.bookmark.model.Room

class FilterRoomAdapter(
    val callback: (roomId: Int, isChecked: Boolean) -> Unit
) : ListAdapter<Room, FilterRoomAdapter.ViewHolder>(FilterRoomItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderRoomFilterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_room_filter,
            parent,
            false
        )
        viewDataBinding.layoutMain.setOnClickListener {
            callback.invoke(
                viewDataBinding.holder!!.roomId,
                viewDataBinding.roomCheck.isChecked
            )
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderRoomFilterBinding) :
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