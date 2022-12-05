package com.depromeet.knockknock.ui.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderBookmarkBinding
import com.depromeet.knockknock.databinding.HolderInviteFriendBinding
import com.depromeet.knockknock.databinding.HolderInviteRoomBinding
import com.depromeet.knockknock.databinding.HolderNotificationAlarmBinding
import com.depromeet.knockknock.ui.notification.NotificationActionHandler
import com.depromeet.knockknock.ui.notification.model.Notification
import com.depromeet.knockknock.ui.notification.model.NotificationAlarm
import com.depromeet.knockknock.ui.bookmark.model.Bookmark
import com.depromeet.knockknock.ui.notification.model.InviteFriend
import com.depromeet.knockknock.ui.notification.model.InviteRoom
import com.depromeet.knockknock.util.toggleLayout

class NotificationAdapter(
    private val eventListener: NotificationActionHandler
) : ListAdapter<Notification, RecyclerView.ViewHolder>(BookmarkItemDiffCallback){

    companion object {
        private const val NotificationAlarm = 0
        private const val InviteRoom = 1
        private const val InviteFriend = 2
    }

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when(viewType) {
            NotificationAlarm -> {
                val viewDataBinding = HolderNotificationAlarmBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                NotificationAlarmViewHolder(viewDataBinding)
            }
            InviteRoom -> {
                val viewDataBinding = HolderInviteRoomBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                InviteRoomViewHolder(viewDataBinding)
            }
            InviteFriend -> {
                val viewDataBinding = HolderInviteFriendBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                InviteFriendViewHolder(viewDataBinding)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder.itemViewType) {
            0 -> {
                val item = holder as NotificationAlarmViewHolder
                item.bind(getItem(position) as NotificationAlarm)
            }
            1 -> {
                val item = holder as InviteRoomViewHolder
                item.bind(getItem(position) as InviteRoom)
            }
            2 -> {
                val item = holder as InviteFriendViewHolder
                item.bind(getItem(position) as InviteFriend)
            }
        }
    }

    class NotificationAlarmViewHolder(private val binding: HolderNotificationAlarmBinding): ViewHolder(binding.root) {

        fun bind(item: NotificationAlarm) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    class InviteRoomViewHolder(private val binding: HolderInviteRoomBinding): ViewHolder(binding.root) {

        fun bind(item: InviteRoom) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    class InviteFriendViewHolder(private val binding: HolderInviteFriendBinding): ViewHolder(binding.root) {

        fun bind(item: InviteFriend) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object BookmarkItemDiffCallback : DiffUtil.ItemCallback<Bookmark>() {
        override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark) =
            oldItem.bookmarkId == newItem.bookmarkId

        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark) =
            oldItem == newItem
    }
}