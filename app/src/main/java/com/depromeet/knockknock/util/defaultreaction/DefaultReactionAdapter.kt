package com.depromeet.knockknock.util.defaultreaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.MyReactionInfo
import com.depromeet.domain.model.Reaction
import com.depromeet.domain.model.ReactionCountInfo
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderDefaultReactionBinding

var checkedId = 0

class DefaultReactionAdapter(
    val isCheckedImage: Int,
    private val eventListener: DefaultReactionActionHandler
) : ListAdapter<Reaction, DefaultReactionAdapter.ViewHolder>(DefaultReactionItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderDefaultReactionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_default_reaction,
            parent,
            false
        )
        checkedId = isCheckedImage

        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderDefaultReactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Reaction) {
            binding.holder = item
            if(item.id == checkedId) {
                binding.isChecked.visibility = View.VISIBLE
            }
        }
    }

    internal object DefaultReactionItemDiffCallback : DiffUtil.ItemCallback<Reaction>() {
        override fun areItemsTheSame(oldItem: Reaction, newItem: Reaction) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Reaction, newItem: Reaction) =
            oldItem == newItem
    }
}