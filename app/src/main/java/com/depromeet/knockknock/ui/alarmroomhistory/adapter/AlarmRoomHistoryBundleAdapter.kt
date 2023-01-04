package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Notification
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.ItemRecyclerHistoryBundleBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryActionHandler
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

class AlarmRoomHistoryBundleAdapter(
    private val eventListener: AlarmRoomHistoryActionHandler,
    private val viewModel: AlarmRoomHistoryViewModel
    ) : PagingDataAdapter<Notification, AlarmRoomHistoryBundleAdapter.ViewHolder>(
    AlarmRoomHistoryBundleItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: ItemRecyclerHistoryBundleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recycler_history_bundle,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, viewModel) }
    }

    class ViewHolder(private val binding: ItemRecyclerHistoryBundleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Notification, viewModel: AlarmRoomHistoryViewModel) {
            binding.holder = item
            binding.executePendingBindings()
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
             * **/

            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat("yyyy년 MM월 dd일") //날짜의 모양을 원하는 대로 변경 해 준다.
            val currentDate = formatter.format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -1) //변경하고 싶은 원하는 날짜 수를 넣어 준다.  
            val yesterdayDate = formatter.format(calendar.time)
            val createdDate = formatter.format(item.created_date)

            if (currentDate == createdDate) {
                binding.recyclerView.adapter = AlarmRoomHistoryMessageAdapter(viewModel)
                binding.holder = item
                binding.textView.text = "오늘"

            } else if (yesterdayDate == currentDate) {
                binding.textView.text = "어제"


            } else {

            }


//            binding.expandBtn.setOnClickListener {
//                binding.holder!!.isExpanded = !(binding.holder!!.isExpanded)
//
//                if(binding.holder!!.isExpanded) {
//                    binding.expandBtn.apply {
//                        this.text = context.getString(R.string.shorts_contents)
//                    }
//                    binding.contentsImg.visibility = View.VISIBLE
//                    binding.contentsText.maxLines = 9999
//                }
//                else {
//                    binding.expandBtn.apply {
//                        this.text = context.getString(R.string.more_contents)
//                    }
//                    binding.contentsImg.visibility = View.GONE
//                    binding.contentsText.maxLines = 2
//                }
//            }
        }
    }
}

internal object AlarmRoomHistoryBundleItemDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
        oldItem.notification_id == newItem.notification_id

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
        oldItem == newItem
}
