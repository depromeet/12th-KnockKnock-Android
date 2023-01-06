package com.depromeet.knockknock.ui.friendlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Friend
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderFriendListBinding
import com.depromeet.knockknock.ui.friendlist.FriendListActionHandler

class FriendListAdapter(
    private val eventListener: FriendListActionHandler
) : ListAdapter<Friend, FriendListAdapter.ViewHolder>(FriendListItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderFriendListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_friend_list,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderFriendListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Friend) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FriendListItemDiffCallback : DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Friend, newItem: Friend) =
            oldItem == newItem
    }
}