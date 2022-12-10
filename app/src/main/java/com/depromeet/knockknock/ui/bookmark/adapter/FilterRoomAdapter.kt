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
import com.depromeet.knockknock.databinding.HolderRoomFilterBinding
import com.depromeet.knockknock.ui.bookmark.BookmarkActionHandler
import com.depromeet.knockknock.ui.bookmark.model.Bookmark
import com.depromeet.knockknock.ui.bookmark.model.Room
import com.depromeet.knockknock.util.ToggleAnimation
import com.depromeet.knockknock.util.toggleLayout

class FilterRoomAdapter(
    val callback: (roomId: Int, isChecked: Boolean) -> Unit
) : ListAdapter<Room, FilterRoomAdapter.ViewHolder>(FilterRoomItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderRoomFilterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_bookmark,
            parent,
            false
        )
        viewDataBinding.roomCheck.setOnClickListener {
            viewDataBinding.holder!!.isCheckd = !viewDataBinding.holder!!.isCheckd
            callback.invoke(
                viewDataBinding.holder!!.roomId,
                viewDataBinding.holder!!.isCheckd
            )
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderRoomFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Room) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FilterRoomItemDiffCallback : DiffUtil.ItemCallback<Room>() {
        override fun areItemsTheSame(oldItem: Room, newItem: Room) =
            oldItem.roomId == newItem.roomId

        override fun areContentsTheSame(oldItem: Room, newItem: Room) =
            oldItem == newItem
    }
}