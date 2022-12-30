package com.depromeet.knockknock.ui.home.bottom.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.GroupContent
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderBottomRoomSelectBinding

class BottomRoomSelectAdapter(
    private val eventLister: BottomRoomSelectActionHandler
) : PagingDataAdapter<GroupContent, BottomRoomSelectAdapter.ViewHolder>(BottomRoomSelectItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderBottomRoomSelectBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_bottom_room_select,
            parent,
            false
        )
        viewDataBinding.eventListener = eventLister
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(private val binding: HolderBottomRoomSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroupContent) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object BottomRoomSelectItemDiffCallback : DiffUtil.ItemCallback<GroupContent>() {
        override fun areItemsTheSame(oldItem: GroupContent, newItem: GroupContent) =
            oldItem.group_id == newItem.group_id

        override fun areContentsTheSame(oldItem: GroupContent, newItem: GroupContent) =
            oldItem == newItem
    }
}