package com.depromeet.knockknock.ui.alarmroomexplore.adapter

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
import com.depromeet.knockknock.databinding.HolderCategoryBinding
import com.depromeet.knockknock.databinding.HolderPopularRoomBinding
import com.depromeet.knockknock.ui.alarmroomexplore.AlarmRoomExploreActionHandler
import com.depromeet.knockknock.ui.alarmroomsearch.AlarmRoomSearchActionHandler
import com.depromeet.knockknock.ui.category.model.Category


class PopularRoomAdapter(
    private val eventListener: AlarmRoomExploreActionHandler
) : ListAdapter<GroupBriefInfo, PopularRoomAdapter.ViewHolder>(RoomListItemDiffCallback){

    init { setHasStableIds(true) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderPopularRoomBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_popular_room,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderPopularRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: GroupBriefInfo) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object RoomListItemDiffCallback : DiffUtil.ItemCallback<GroupBriefInfo>() {
        override fun areItemsTheSame(oldItem: GroupBriefInfo, newItem: GroupBriefInfo) =
            oldItem.group_id == newItem.group_id

        override fun areContentsTheSame(oldItem: GroupBriefInfo, newItem: GroupBriefInfo) =
            oldItem == newItem
    }
}