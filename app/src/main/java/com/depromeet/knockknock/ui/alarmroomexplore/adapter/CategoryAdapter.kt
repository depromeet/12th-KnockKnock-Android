package com.depromeet.knockknock.ui.alarmroomexplore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Category
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderCategoryBinding
import com.depromeet.knockknock.databinding.HolderCategoryRoundBinding
import com.depromeet.knockknock.ui.alarmroomexplore.AlarmRoomExploreActionHandler
import com.depromeet.knockknock.ui.category.CategoryActionHandler

var beforeClicked = 1

class CategoryAdapter(
    private val eventListener: AlarmRoomExploreActionHandler
) : ListAdapter<Category, CategoryAdapter.ViewHolder>(FriendListItemDiffCallback){

    init { setHasStableIds(true) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCategoryRoundBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_category_round,
            parent,
            false
        )
        viewDataBinding.layoutMain.setOnClickListener {
            beforeClicked = viewDataBinding.holder!!.id
            eventListener.onCategoryClicked(viewDataBinding.holder!!.id)
            this.notifyDataSetChanged()
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderCategoryRoundBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Category) {
            binding.holder = item

            if(beforeClicked == binding.holder!!.id)
                binding.categoryEmojiFrame.background = binding.root.context.getDrawable(R.drawable.custom_backgroundwhite_radius80_line_yellow)
            else
                binding.categoryEmojiFrame.background = binding.root.context.getDrawable(R.drawable.custom_backgroundwhite_radius80)

            binding.executePendingBindings()
        }
    }

    internal object FriendListItemDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
            oldItem == newItem
    }
}