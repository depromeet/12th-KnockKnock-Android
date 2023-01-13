package com.depromeet.knockknock.ui.invitefriendtoroom.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Friend
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderFriendListInviteBinding
import com.depromeet.knockknock.ui.invitefriendtoroom.InviteFriendToRoomActionHandler

var userIdxList : MutableList<Int> = mutableListOf()

class InviteFriendToRoomAdapter(
    private val eventListener: InviteFriendToRoomActionHandler
) : ListAdapter<Friend, InviteFriendToRoomAdapter.ViewHolder>(InviteFriendListItemDiffCallback){

    init { setHasStableIds(true) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderFriendListInviteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_friend_list_invite,
            parent,
            false
        )
        viewDataBinding.layoutMain.setOnClickListener {
            val clickedUserIdx = viewDataBinding.holder!!.id
            if (clickedUserIdx in userIdxList)
                userIdxList.remove(clickedUserIdx)
            else {
                userIdxList.add(clickedUserIdx)
            }
            eventListener.onInviteFriendClicked(
                userIdx = viewDataBinding.holder!!.id,
                isChecked = viewDataBinding.holder!!.id in userIdxList
            )
            this.notifyDataSetChanged()
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderFriendListInviteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Friend) {
            binding.holder = item

            if(binding.holder!!.id in userIdxList)
                binding.friendIsChecked.background = binding.root.context.getDrawable(R.drawable.ic_check_circle_yellow)
            else
                binding.friendIsChecked.background = binding.root.context.getDrawable(R.drawable.ic_check_circle)
            binding.executePendingBindings()
        }

    }

    internal object InviteFriendListItemDiffCallback : DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Friend, newItem: Friend) =
            oldItem == newItem
    }
}