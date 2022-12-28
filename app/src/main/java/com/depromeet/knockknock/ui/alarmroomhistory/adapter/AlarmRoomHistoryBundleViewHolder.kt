package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.databinding.ItemRecyclerHistoryBundleBinding
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryBundle

class AlarmRoomHistoryBundleViewHolder(
    private val binding: ItemRecyclerHistoryBundleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HistoryBundle, viewModel: AlarmRoomHistoryViewModel, b: Boolean) {
        binding.apply {
            recyclerView.adapter = AlarmRoomHistoryMessageAdapter(viewModel)
            model = item
            vm = viewModel
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