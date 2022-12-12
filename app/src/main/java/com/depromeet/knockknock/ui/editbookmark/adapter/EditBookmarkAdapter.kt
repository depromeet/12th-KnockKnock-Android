package com.depromeet.knockknock.ui.editbookmark.adapter

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
import com.depromeet.knockknock.databinding.HolderEditBookmarkBinding
import com.depromeet.knockknock.ui.bookmark.BookmarkActionHandler
import com.depromeet.knockknock.ui.bookmark.adapter.BookmarkAdapter
import com.depromeet.knockknock.ui.bookmark.model.Bookmark
import com.depromeet.knockknock.ui.editbookmark.EditBookmarkActionHandler
import com.depromeet.knockknock.ui.editbookmark.model.EditBookmark
import com.depromeet.knockknock.util.ToggleAnimation
import com.depromeet.knockknock.util.toggleLayout

class EditBookmarkAdapter(
    private val eventListener: EditBookmarkActionHandler
) : ListAdapter<EditBookmark, EditBookmarkAdapter.ViewHolder>(EditBookmarkItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderEditBookmarkBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_edit_bookmark,
            parent,
            false
        )
        viewDataBinding.layoutMain.setOnClickListener {
            eventListener.onCheckClicked(
                bookmarkIdx = viewDataBinding.holder!!.bookmarkId,
                isChecked = viewDataBinding.checkBtn.isChecked
            )
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderEditBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EditBookmark) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object EditBookmarkItemDiffCallback : DiffUtil.ItemCallback<EditBookmark>() {
        override fun areItemsTheSame(oldItem: EditBookmark, newItem: EditBookmark) =
            oldItem.bookmarkId == newItem.bookmarkId

        override fun areContentsTheSame(oldItem: EditBookmark, newItem: EditBookmark) =
            oldItem == newItem
    }
}