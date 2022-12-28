package com.depromeet.knockknock.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Notification
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderBookmarkBinding
import com.depromeet.knockknock.ui.bookmark.BookmarkActionHandler
import com.depromeet.knockknock.ui.bookmark.model.Bookmark
import com.depromeet.knockknock.util.ToggleAnimation
import com.depromeet.knockknock.util.toggleLayout

class BookmarkAdapter(
    private val eventListener: BookmarkActionHandler
) : PagingDataAdapter<Notification, BookmarkAdapter.ViewHolder>(BookmarkItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderBookmarkBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_bookmark,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(private val binding: HolderBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Notification) {
            binding.holder = item
            binding.executePendingBindings()
            binding.expandBtn.setOnClickListener {
                binding.holder!!.isExpanded = !(binding.holder!!.isExpanded)

                if(binding.holder!!.isExpanded) {
                    binding.expandBtn.apply {
                        this.text = context.getString(R.string.shorts_contents)
                    }
                    binding.contentsImg.visibility = View.VISIBLE
                    binding.contentsText.maxLines = 9999
                }
                else {
                    binding.expandBtn.apply {
                        this.text = context.getString(R.string.more_contents)
                    }
                    binding.contentsImg.visibility = View.GONE
                    binding.contentsText.maxLines = 2
                }
            }
        } }
}

internal object BookmarkItemDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
        oldItem.notification_id == newItem.notification_id

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
        oldItem == newItem
}
