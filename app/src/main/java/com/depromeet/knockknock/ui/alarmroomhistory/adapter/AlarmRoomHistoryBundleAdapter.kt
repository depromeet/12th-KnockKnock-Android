package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Notification
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.ItemRecyclerHistoryBundleBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryActionHandler
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * 페이징을 통해 날짜와 그 외 히스토리 데이터를 받아온다.
 * 날짜는 AlarmRoomHistoryBundleAdapter 를 통해 화면에 뿌려주고
 * AlarmRoomHistoryBundleAdapter 안에 있는 AlarmRoomHistoryMessageAdapter 를 통해 히스토리 정보를 화면에 뿌려준다.
 * 좋다 그러면 이제 어떻게 해야하는가?..
 *
 * 1. 히스토리 날짜가 오늘, 어제와 같은지 확인한다.
 * 2. 날짜가 다르면 AlarmRoomHistoryBundleAdapter 와 AlarmRoomHistoryMessageAdapter 호출
 * 3. 날짜가 같으면 AlarmRoomHistoryMessageAdapter 호출
 * 4. 화면에 뿌려지는 날짜가 변경되면 AlarmRoomHistoryBundleAdapter 와 AlarmRoomHistoryMessageAdapter 호출
 * 5. 화면에 뿌려지는 날짜가 이전에 뿌린 날짜와 같으면 AlarmRoomHistoryMessageAdapter 호출
 *
 * 1, 2, 3번은 처음에만 중요하고 그 이후에는 4, 5번이 반복되면서 API 호출 및 UI
 *
 * 리사이클러뷰는 아이템마다 개수가 나온다...
 * 음...
 *
 * **/

class AlarmRoomHistoryBundleAdapter(
    private val eventListener: AlarmRoomHistoryActionHandler,
    private val viewModel: AlarmRoomHistoryViewModel
) : PagingDataAdapter<Notification, AlarmRoomHistoryBundleAdapter.ViewHolder>(
    AlarmRoomHistoryBundleItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("ttt 알림방 히스토리", "들어와라")

        val viewDataBinding: ItemRecyclerHistoryBundleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recycler_history_bundle,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("ttt 알림방 히스토리0", holder.toString())

        getItem(position)?.let {
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
            val currentDate = LocalDate.now().format(formatter)
            val yesterdayDate = LocalDate.now().minusDays(1).format(formatter)
            val createdDate =
                LocalDate.parse(it.created_date.substring(0, 10), DateTimeFormatter.ISO_DATE)
                    .format(formatter)
            Log.d("ttt currentDate", currentDate)
            Log.d("ttt yesterdayDate", yesterdayDate)
            Log.d("ttt createdDate", createdDate)

            if (currentDate == createdDate) {
                holder.bind(it, viewModel, true, createdDate)
            } else if (yesterdayDate == createdDate) {
                holder.bind(it, viewModel, true, createdDate)
            } else {
                holder.bind(it, viewModel, false, createdDate)
            }
        }
    }

    class ViewHolder(private val binding: ItemRecyclerHistoryBundleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Notification, viewModel: AlarmRoomHistoryViewModel, b: Boolean, date: String) {
            Log.d("ttt 알림방 히스토리1", item.toString())

            binding.apply {
                recyclerView.adapter = AlarmRoomHistoryMessageAdapter(viewModel)
                holder = item
                vm = viewModel
                dateText.text = date


                executePendingBindings()

                if (b) {
                    recyclerView.visibility = View.VISIBLE
                    ivFold.animate().setDuration(200).rotation(180f)
                } else {
                    recyclerView.visibility = View.GONE
                    ivFold.animate().setDuration(200).rotation(0f)

                }

                ivFold.setOnClickListener {
                    if (recyclerView.visibility == View.VISIBLE) {
                        recyclerView.visibility = View.GONE
                        ivFold.animate().setDuration(200).rotation(0f)
                    } else {
                        recyclerView.visibility = View.VISIBLE
                        ivFold.animate().setDuration(200).rotation(180f)
                    }
                }
            }
        }
    }
}

internal object AlarmRoomHistoryBundleItemDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
        oldItem.notification_id == newItem.notification_id

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
        oldItem == newItem
}
