package com.depromeet.knockknock.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Notification
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderHomeRecentBinding
import com.depromeet.knockknock.ui.home.HomeActionHandler

class HomeRecentAdapter(
    private val eventLister: HomeActionHandler
) : ListAdapter<Notification, HomeRecentAdapter.ViewHolder>(FilterHomeRecentItemDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderHomeRecentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_home_recent,
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

        fun bind(item: Notification) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FilterHomeRecentItemDiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem.notification_id == newItem.notification_id

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem == newItem
    }
}