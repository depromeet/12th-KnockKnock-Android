package com.depromeet.knockknock.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.GroupContent
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderRoomFilterBinding
import com.depromeet.knockknock.ui.bookmark.bottom.BottomRoomActionHandler

class FilterRoomAdapter(
    val eventListener: BottomRoomActionHandler
) : PagingDataAdapter<GroupContent, FilterRoomAdapter.ViewHolder>(FilterRoomItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderRoomFilterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_room_filter,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(private val binding: HolderRoomFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroupContent) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FilterRoomItemDiffCallback : DiffUtil.ItemCallback<GroupContent>() {
        override fun areItemsTheSame(oldItem: GroupContent, newItem: GroupContent) =
            oldItem.group_id == newItem.group_id

        override fun areContentsTheSame(oldItem: GroupContent, newItem: GroupContent) =
            oldItem == newItem
    }
}