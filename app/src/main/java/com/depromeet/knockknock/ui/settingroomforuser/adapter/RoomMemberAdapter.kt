package com.depromeet.knockknock.ui.settingroomforuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Member
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderMemberListBinding
import com.depromeet.knockknock.ui.settingroomforuser.SettingRoomForUserActionHandler

class RoomMemberAdapter(
    val eventListener: SettingRoomForUserActionHandler
) : ListAdapter<Member, RoomMemberAdapter.ViewHolder>(FriendListItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderMemberListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_member_list,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(private val binding: HolderMemberListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Member) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FriendListItemDiffCallback : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member) =
            oldItem.user_id == newItem.user_id

        override fun areContentsTheSame(oldItem: Member, newItem: Member) =
            oldItem == newItem
    }
}