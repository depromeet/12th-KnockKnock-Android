package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.domain.model.Admission
import com.depromeet.knockknock.databinding.ItemRecyclerInviteRoomBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryActionHandler
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AlarmInviteRoomAdapter(
    private val eventListener: AlarmRoomHistoryActionHandler,
    private val viewModel: AlarmRoomHistoryViewModel
) : ListAdapter<Admission, AlarmInviteRoomViewHolder>(AlarmInviteRoomItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmInviteRoomViewHolder {
        return AlarmInviteRoomViewHolder(
            ItemRecyclerInviteRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                eventListener = this@AlarmInviteRoomAdapter.eventListener
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AlarmInviteRoomViewHolder, position: Int) {
        getItem(position)?.let { item ->
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
            val currentDate = LocalDate.now().format(formatter)
            val createdDate =
                LocalDate.parse(item.created_at.substring(0, 10), DateTimeFormatter.ISO_DATE)
                    .format(formatter)

            if (currentDate == createdDate) {
                holder.bind(
                    item,
                    viewModel,
                    LocalDateTime.parse(item.created_at, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern("오늘 a hh:mm"))
                )
            } else {
                holder.bind(
                    item,
                    viewModel,
                    LocalDateTime.parse(item.created_at, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern("MM월 dd일 a hh:mm"))
                )
            }

        }
    }

    internal object AlarmInviteRoomItemDiffCallback : DiffUtil.ItemCallback<Admission>() {
        override fun areItemsTheSame(oldItem: Admission, newItem: Admission) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Admission, newItem: Admission) =
            oldItem.equals(newItem)
    }
}