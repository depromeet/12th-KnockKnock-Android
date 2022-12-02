package com.depromeet.knockknock.ui.addfriend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderFriendAddBinding
import com.depromeet.knockknock.ui.addfriend.AddFriendActionHandler
import com.depromeet.knockknock.ui.friendlist.FriendListActionHandler
import com.depromeet.knockknock.ui.friendlist.model.User

class FriendAddAdapter(
    private val eventListener: AddFriendActionHandler
) : ListAdapter<User, FriendAddAdapter.ViewHolder>(FriendListItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderFriendAddBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_bookmark,
            parent,
            false
        )
        viewDataBinding.addBtn.setOnClickListener {
            eventListener.onAddFriendClicked(viewDataBinding.holder.userIdx)
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderFriendAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FriendListItemDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.userIdx == newItem.userIdx

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
    }
}