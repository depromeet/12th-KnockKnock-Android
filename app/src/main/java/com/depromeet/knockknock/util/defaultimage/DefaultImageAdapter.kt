package com.depromeet.knockknock.util.defaultimage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Profile
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderDefaultImageBinding

var checkedId = 0

class DefaultImageAdapter(
    val isCheckedImage: Profile,
    private val eventListener: DefaultImageActionHandler
) : ListAdapter<Profile, DefaultImageAdapter.ViewHolder>(DefaultImageItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderDefaultImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_default_image,
            parent,
            false
        )
        checkedId = isCheckedImage.id

        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderDefaultImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Profile) {
            binding.holder = item
            if(item.id == checkedId) {
                binding.isChecked.visibility = View.VISIBLE
            }
        }
    }

    internal object DefaultImageItemDiffCallback : DiffUtil.ItemCallback<Profile>() {
        override fun areItemsTheSame(oldItem: Profile, newItem: Profile) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Profile, newItem: Profile) =
            oldItem == newItem
    }
}