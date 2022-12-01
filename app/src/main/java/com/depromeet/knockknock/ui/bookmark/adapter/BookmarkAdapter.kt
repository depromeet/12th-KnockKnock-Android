package com.dida.android.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderHotsBinding
import com.dida.android.presentation.views.nav.home.HomeActionHandler
import com.dida.domain.model.nav.home.Hots

class HotsAdapter(
    private val eventListener: HomeActionHandler
) : ListAdapter<Hots, HotsAdapter.ViewHolder>(HotsItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderHotsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_hots,
            parent,
            false
        )
        viewDataBinding.root.setOnClickListener {
            eventListener.onHotItemClicked(viewDataBinding.holderModel!!.cardId.toLong())
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderHotsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Hots) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object HotsItemDiffCallback : DiffUtil.ItemCallback<Hots>() {
        override fun areItemsTheSame(oldItem: Hots, newItem: Hots) =
            oldItem.cardId == newItem.cardId

        override fun areContentsTheSame(oldItem: Hots, newItem: Hots) =
            oldItem == newItem
    }
}