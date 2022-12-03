package com.depromeet.knockknock.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderBookmarkBinding
import com.depromeet.knockknock.ui.bookmark.BookmarkActionHandler
import com.depromeet.knockknock.ui.bookmark.model.Bookmark
import com.depromeet.knockknock.util.ToggleAnimation
import com.depromeet.knockknock.util.toggleLayout

class BookmarkAdapter(
    private val eventListener: BookmarkActionHandler
) : ListAdapter<Bookmark, BookmarkAdapter.ViewHolder>(BookmarkItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderBookmarkBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_bookmark,
            parent,
            false
        )
        viewDataBinding.root.setOnClickListener {
            eventListener.onReactionClicked(viewDataBinding.holder!!.bookmarkId)
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Bookmark) {
            binding.holder = item
            binding.executePendingBindings()
            binding.expandBtn.setOnClickListener {
                val show = toggleLayout(!item.isExpanded, it, binding.layoutExpand)
                item.isExpanded = show
            }
        }
    }

    internal object BookmarkItemDiffCallback : DiffUtil.ItemCallback<Bookmark>() {
        override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark) =
            oldItem.bookmarkId == newItem.bookmarkId

        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark) =
            oldItem == newItem
    }
}