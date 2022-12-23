package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.databinding.ItemRecycleHistoryBundleBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryActionHandler
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryBundle

class AlarmRoomHistoryBundleViewHolder(
    private val binding: ItemRecycleHistoryBundleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HistoryBundle,  viewModel: AlarmRoomHistoryViewModel) {
        val alarmRoomHistoryMessageAdapter by lazy { AlarmRoomHistoryMessageAdapter(viewModel) }

        binding.recyclerView.adapter =  AlarmRoomHistoryMessageAdapter(viewModel)
        binding.apply {
            model = item
            vm = viewModel
            executePendingBindings()
        }
    }
}