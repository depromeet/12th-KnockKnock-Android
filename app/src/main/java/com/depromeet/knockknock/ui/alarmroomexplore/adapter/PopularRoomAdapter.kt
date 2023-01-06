package com.depromeet.knockknock.ui.alarmroomexplore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.GroupBriefInfo
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderPopularRoomBinding
import com.depromeet.knockknock.ui.alarmroomexplore.AlarmRoomExploreActionHandler

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
        viewDataBinding.layoutMain.setOnClickListener {
            eventListener.onRoomClicked(viewDataBinding.holder!!.group_id)
            this.notifyDataSetChanged()
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
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