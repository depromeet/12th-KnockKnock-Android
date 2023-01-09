package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Notification
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.ItemRecyclerHistoryBundleBinding
import com.depromeet.knockknock.databinding.ItemRecyclerHistoryMessageBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryActionHandler
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AlarmRoomHistoryMessageAdapter(
    private val eventListener: AlarmRoomHistoryActionHandler,
    private val viewModel: AlarmRoomHistoryViewModel
) : PagingDataAdapter<Notification, AlarmRoomHistoryMessageAdapter.ViewHolder>(
    AlarmRoomHistoryMessageItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("ttt 알림방 히스토리", "들어와라")

        val viewDataBinding: ItemRecyclerHistoryMessageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recycler_history_message,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("ttt 알림방 히스토리1", holder.toString())

        getItem(position)?.let {
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
            val currentDate = LocalDate.now().format(formatter)
            val createdDate =
                LocalDate.parse(it.created_date.substring(0, 10), DateTimeFormatter.ISO_DATE)
                    .format(formatter)
            Log.d("ttt currentDate", currentDate)
            Log.d("ttt createdDate", createdDate)

            if (currentDate == createdDate) {
                holder.bind(
                    it,
                    viewModel,
                    true,
                    LocalDateTime.parse(it.created_date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern("오늘 a hh:mm"))
                )
            } else {
                holder.bind(
                    it,
                    viewModel,
                    false,
                    LocalDateTime.parse(it.created_date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern("MM월 dd일 a hh:mm"))
                )
            }
        }
    }

    class ViewHolder(private val binding: ItemRecyclerHistoryMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(
            item: Notification,
            viewModel: AlarmRoomHistoryViewModel,
            b: Boolean,
            date: String
        ) {
            Log.d("ttt 알림방 히스토리2", item.toString())

            binding.apply {
                model = item
                vm = viewModel
                viewModel.alarmDateEvent.value = date
                viewModel.alarmRoomTitleEvent.value = item.groups.title
                viewModel.alarmRoomDescriptionEvent.value = item.groups.description
                executePendingBindings()
                expandBtn.setOnClickListener {
                    model!!.isExpanded = !(model!!.isExpanded)

                    if (model!!.isExpanded) {
                        expandBtn.apply {
                            this.text = context.getString(R.string.shorts_contents)
                        }
                        contents.maxLines = 9999
                    } else {
                        expandBtn.apply {
                            this.text = context.getString(R.string.more_contents)
                        }
                        contents.maxLines = 2
                    }
                }
            }
        }
    }
}

internal object AlarmRoomHistoryMessageItemDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
        oldItem.notification_id == newItem.notification_id

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
        oldItem == newItem
}
