package com.depromeet.knockknock.ui.invitefriendtoroom.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderFriendAddBinding
import com.depromeet.knockknock.databinding.HolderFriendListInviteBinding
import com.depromeet.knockknock.ui.editroomdetails.adapter.beforeClicked
import com.depromeet.knockknock.ui.editroomdetails.model.Background
import com.depromeet.knockknock.ui.friendlist.model.User
import com.depromeet.knockknock.ui.invitefriendtoroom.InviteFriendToRoomActionHandler

var userIdxList : MutableList<Int> = mutableListOf()

class InviteFriendToRoomAdapter(
    private val eventListener: InviteFriendToRoomActionHandler
) : ListAdapter<User, InviteFriendToRoomAdapter.ViewHolder>(InviteFriendListItemDiffCallback){

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
            val clickedUserIdx = viewDataBinding.holder!!.userIdx
            if (clickedUserIdx in userIdxList)
                userIdxList.remove(clickedUserIdx)
            else {
                userIdxList.add(clickedUserIdx)
            }
            eventListener.onInviteFriendClicked(
                userIdx = viewDataBinding.holder!!.userIdx,
                isChecked = viewDataBinding.holder!!.userIdx in userIdxList
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
        fun bind(item: User) {
            binding.holder = item

            if(binding.holder!!.userIdx in userIdxList)
                binding.friendIsChecked.background = binding.root.context.getDrawable(R.drawable.ic_check_circle_yellow)
            else
                binding.friendIsChecked.background = binding.root.context.getDrawable(R.drawable.ic_check_circle)
            binding.executePendingBindings()
        }

    }

    internal object InviteFriendListItemDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.userIdx == newItem.userIdx

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
    }
}