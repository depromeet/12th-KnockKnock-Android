package com.depromeet.knockknock.ui.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Alarm
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderInviteFriendBinding
import com.depromeet.knockknock.databinding.HolderInviteRoomBinding
import com.depromeet.knockknock.databinding.HolderNotificationAlarmBinding
import com.depromeet.knockknock.ui.notification.NotificationActionHandler
import com.depromeet.knockknock.ui.notification.model.*

class NotificationAdapter(
    private val eventListener: NotificationActionHandler
) : ListAdapter<Alarm, NotificationViewHolder>(NotificationItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return when(viewType) {
            R.layout.holder_invite_room -> {
                NotificationViewHolder.InviteRoomViewHolder(
                    HolderInviteRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                        eventListener = this@NotificationAdapter.eventListener
                    }
                )
            }
            R.layout.holder_invite_friend -> {
                NotificationViewHolder.InviteFriendViewHolder(
                    HolderInviteFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                        eventListener = this@NotificationAdapter.eventListener
                    }
                )
            }
            else -> {
                NotificationViewHolder.NotificationAlarmViewHolder(
                    HolderNotificationAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                        eventListener = this@NotificationAdapter.eventListener
                    }
                )
            }
        }
    }


    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when(getItem(position).type) {
            NotificationType.INVITEROOM.toString() -> R.layout.holder_invite_room
            NotificationType.INVITEFRIEND.toString() -> R.layout.holder_invite_friend
            NotificationType.NOTIFICATIONALARM.toString() -> R.layout.holder_notification_alarm
            else -> 0
        }

    internal object NotificationItemDiffCallback : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm) =
            oldItem.type == newItem.type

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm) =
            oldItem == newItem
    }
}

sealed class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun bind(notification: Alarm) = Unit

    class NotificationAlarmViewHolder(private val binding: HolderNotificationAlarmBinding): NotificationViewHolder(binding.root) {

        override fun bind(alarm: Alarm) {
            binding.holder = alarm
            binding.executePendingBindings()
        }
    }

    class InviteRoomViewHolder(private val binding: HolderInviteRoomBinding): NotificationViewHolder(binding.root) {

        override fun bind(alarm: Alarm) {
            binding.holder = alarm
            binding.executePendingBindings()
        }
    }

    class InviteFriendViewHolder(private val binding: HolderInviteFriendBinding): NotificationViewHolder(binding.root) {

        override fun bind(alarm: Alarm) {
            binding.holder = alarm
            binding.executePendingBindings()
        }
    }

}