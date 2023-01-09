package com.depromeet.knockknock.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Notification
import com.depromeet.domain.model.ReactionCountInfo
import com.depromeet.domain.model.Reactions
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderBookmarkBinding
import com.depromeet.knockknock.databinding.HolderReactionBinding
import com.depromeet.knockknock.ui.bookmark.BookmarkActionHandler

class ReactionAdapter(
    private val eventListener: BookmarkActionHandler
) : PagingDataAdapter<ReactionCountInfo, ReactionAdapter.ViewHolder>(ReactionItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderReactionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_reaction,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(private val binding: HolderReactionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReactionCountInfo) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }
}

internal object ReactionItemDiffCallback : DiffUtil.ItemCallback<ReactionCountInfo>() {
    override fun areItemsTheSame(oldItem: ReactionCountInfo, newItem: ReactionCountInfo) =
        oldItem.reaction_id == newItem.reaction_id

    override fun areContentsTheSame(oldItem: ReactionCountInfo, newItem: ReactionCountInfo) =
        oldItem == newItem
}
