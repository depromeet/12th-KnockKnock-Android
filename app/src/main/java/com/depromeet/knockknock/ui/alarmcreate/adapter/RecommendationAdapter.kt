package com.depromeet.knockknock.ui.alarmcreate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.knockknock.databinding.ItemRecyclerRecommendationMessageBinding
import com.depromeet.knockknock.ui.alarmcreate.AlarmCreateActionHandler
import com.depromeet.knockknock.ui.alarmcreate.model.RecommendationMessage

class RecommendationAdapter(
    private val eventListener: AlarmCreateActionHandler,
) : ListAdapter<RecommendationMessage, RecommendationViewHolder>(RecommendationItemDiffCallback){

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

    internal object RecommendationItemDiffCallback : DiffUtil.ItemCallback<RecommendationMessage>() {
        override fun areItemsTheSame(oldItem: RecommendationMessage, newItem: RecommendationMessage) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: RecommendationMessage, newItem: RecommendationMessage) =
            oldItem.equals(newItem)
    }
}