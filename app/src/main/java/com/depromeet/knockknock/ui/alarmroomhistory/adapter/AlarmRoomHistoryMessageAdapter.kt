package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.depromeet.domain.model.Notification
import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.ItemRecyclerHistoryMessageBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryActionHandler
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryFragment
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryFragmentDirections
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel
import com.depromeet.knockknock.ui.bookmark.adapter.ReactionAdapter
import com.depromeet.knockknock.ui.pushdetail.PushDetailFragment
import kotlinx.coroutines.launch
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
        getItem(position)?.let {
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
            val currentDate = LocalDate.now().format(formatter)
            val createdDate =
                LocalDate.parse(it.created_date.substring(0, 10), DateTimeFormatter.ISO_DATE)
                    .format(formatter)

            if (currentDate == createdDate) {
                holder.bind(
                    it,
                    viewModel,
                    LocalDateTime.parse(it.created_date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern("오늘 a hh:mm"))
                )
            } else {
                holder.bind(
                    it,
                    viewModel,
                    LocalDateTime.parse(it.created_date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern("MM월 dd일 a hh:mm"))
                )
            }
        }
    }

    class ViewHolder(private val binding: ItemRecyclerHistoryMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val reactionAdapter = ReactionAdapter()

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(
            item: Notification,
            viewModel: AlarmRoomHistoryViewModel,
            date: String
        ) {

            binding.apply {
                model = item
                executePendingBindings()
                reactionBtn.setOnClickListener {
                    if (item.reactions.my_reaction_info == null) viewModel.onReactionClicked(
                        notification_id = item.notification_id,
                        reaction_id = 0,
                        notification_reaction_id = 0
                    )
                    else viewModel.onReactionClicked(
                        notification_id =  item.notification_id,
                        reaction_id= item.reactions.my_reaction_info!!.reaction_id,
                        notification_reaction_id= item.reactions.my_reaction_info!!.notification_reaction_id
                    )
                }
                vm = viewModel
                viewModel.alarmDateEvent.value = date
                reactionRecycler.adapter = reactionAdapter
                reactionAdapter.submitList(item.reactions.reaction_count_infos)
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

                // 상세 페이지로 이동
                layoutMain.setOnClickListener {
                    val fragment = it.findFragment<AlarmRoomHistoryFragment>()
                    val username = userNameText.text.toString()
                    val dateTime = dateTimeText.text.toString()
                    val contents = contents.text.toString()
                    val imgContent = contentsImg.toString()
                    val action = AlarmRoomHistoryFragmentDirections.actionAlarmRoomHistoryFragmentToPushDetailFragment(fragment.viewModel.groupId.value,
                    username, dateTime, contents, imgContent)
                    // findNavController(fragment).navigate(R.id.action_alarmRoomHistoryFragment_to_pushDetailFragment)
                    findNavController(fragment).navigate(action)
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
