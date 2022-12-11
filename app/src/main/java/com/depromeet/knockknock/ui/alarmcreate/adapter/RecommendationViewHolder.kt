package com.depromeet.knockknock.ui.alarmcreate.adapter

import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.databinding.ItemRecyclerRecommendationMessageBinding
import com.depromeet.knockknock.ui.alarmcreate.model.RecommendationMessage

class RecommendationViewHolder(
    private val binding: ItemRecyclerRecommendationMessageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RecommendationMessage) {
        binding.apply {
            model = item
            executePendingBindings()
        }
    }
}