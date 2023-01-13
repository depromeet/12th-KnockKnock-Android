package com.depromeet.knockknock.ui.alarmcreate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.domain.model.RecommendMessage
import com.depromeet.knockknock.databinding.ItemRecyclerRecommendationMessageBinding
import com.depromeet.knockknock.ui.alarmcreate.AlarmCreateActionHandler

class RecommendationAdapter(
    private val eventListener: AlarmCreateActionHandler,
) : ListAdapter<RecommendMessage, RecommendationViewHolder>(RecommendationItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        return RecommendationViewHolder(
            ItemRecyclerRecommendationMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                eventListener = this@RecommendationAdapter.eventListener
            }
        )
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    internal object RecommendationItemDiffCallback : DiffUtil.ItemCallback<RecommendMessage>() {
        override fun areItemsTheSame(oldItem: RecommendMessage, newItem: RecommendMessage) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: RecommendMessage, newItem: RecommendMessage) =
            oldItem.equals(newItem)
    }
}