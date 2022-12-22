package com.depromeet.knockknock.ui.editroomdetails.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderEditRoomThumbnailBinding
import com.depromeet.knockknock.ui.editroomdetails.EditRoomDetailsActionHandler
import com.depromeet.knockknock.ui.editroomdetails.model.Thumbnail

var ThumbnailbeforeClicked = 0

class ThumbnailAdapter(
    private val eventListener: EditRoomDetailsActionHandler
) : ListAdapter<Thumbnail, ThumbnailAdapter.ViewHolder>(FriendListItemDiffCallback){

    init { setHasStableIds(true) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderEditRoomThumbnailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_edit_room_thumbnail,
            parent,
            false
        )
        viewDataBinding.layoutMain.setOnClickListener {
            ThumbnailbeforeClicked = viewDataBinding.holder!!.thumbnailId
            eventListener.onThumbnailClicked(viewDataBinding.holder!!.thumbnailId)
            this.notifyDataSetChanged()
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderEditRoomThumbnailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Thumbnail) {
            binding.holder = item

            if(ThumbnailbeforeClicked == binding.holder!!.thumbnailId)
                binding.layoutMain.background = binding.root.context.getDrawable(R.drawable.custom_transparents_radius04_line_yellow)
            else
                binding.layoutMain.background = binding.root.context.getDrawable(R.drawable.custom_transparents_radius04_line_transparent)

            binding.executePendingBindings()
        }
    }

    internal object FriendListItemDiffCallback : DiffUtil.ItemCallback<Thumbnail>() {
        override fun areItemsTheSame(oldItem: Thumbnail, newItem: Thumbnail) =
            oldItem.thumbnailId == newItem.thumbnailId

        override fun areContentsTheSame(oldItem: Thumbnail, newItem: Thumbnail) =
            oldItem == newItem
    }
}