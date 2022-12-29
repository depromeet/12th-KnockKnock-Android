package com.depromeet.knockknock.ui.addfriend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Friend
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderFriendAddBinding
import com.depromeet.knockknock.ui.addfriend.AddFriendActionHandler
import com.depromeet.knockknock.ui.friendlist.FriendListActionHandler
import com.depromeet.knockknock.ui.friendlist.model.User

class FriendAddAdapter(
    private val eventListener: AddFriendActionHandler
) : ListAdapter<Friend, FriendAddAdapter.ViewHolder>(FriendListItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderFriendAddBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_bookmark,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderFriendAddBinding) :
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