package com.depromeet.knockknock.ui.home.adapter

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
import com.depromeet.knockknock.databinding.HolderHomeRecentBinding
import com.depromeet.knockknock.databinding.HolderHomeRoomBinding
import com.depromeet.knockknock.databinding.HolderRoomFilterBinding
import com.depromeet.knockknock.ui.bookmark.BookmarkActionHandler
import com.depromeet.knockknock.ui.bookmark.model.Bookmark
import com.depromeet.knockknock.ui.bookmark.model.Room
import com.depromeet.knockknock.ui.home.HomeActionHandler
import com.depromeet.knockknock.ui.home.model.Alarm
import com.depromeet.knockknock.util.ToggleAnimation
import com.depromeet.knockknock.util.toggleLayout

class HomeRecentAdapter(
    private val eventLister: HomeActionHandler
) : ListAdapter<Alarm, HomeRecentAdapter.ViewHolder>(FilterHomeRecentItemDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderHomeRecentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_home_room,
            parent,
            false
        )
        viewDataBinding.eventListener = eventLister
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderHomeRecentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Alarm) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FilterHomeRecentItemDiffCallback : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm) =
            oldItem.alarmId == newItem.alarmId

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm) =
            oldItem == newItem
    }
}