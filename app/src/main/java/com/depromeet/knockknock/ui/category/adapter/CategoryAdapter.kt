package com.depromeet.knockknock.ui.category.adapter

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
import com.depromeet.knockknock.ui.category.CategoryActionHandler

var beforeClicked = 0

class CategoryAdapter(
    private val eventListener: CategoryActionHandler
) : ListAdapter<Category, CategoryAdapter.ViewHolder>(FriendListItemDiffCallback){

    init { setHasStableIds(true) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_category,
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

    class ViewHolder(private val binding: HolderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Category) {
            binding.holder = item

            if(beforeClicked == binding.holder!!.id)
                binding.layoutMain.background = binding.root.context.getDrawable(R.drawable.custom_yellow_radius16_line_gray04)
            else
                binding.layoutMain.background = binding.root.context.getDrawable(R.drawable.custom_transparents_radius16_line_gray04)

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