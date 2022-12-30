package com.depromeet.knockknock.ui.editroomdetails.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Background
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderEditRoomBackgroundBinding
import com.depromeet.knockknock.ui.editroomdetails.EditRoomDetailsActionHandler

var beforeClicked = 0

class BackgroundAdapter(
    private val eventListener: EditRoomDetailsActionHandler
) : ListAdapter<Background, BackgroundAdapter.ViewHolder>(BackgroundListItemDiffCallback){

    init { setHasStableIds(true) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderEditRoomBackgroundBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_edit_room_background,
            parent,
            false
        )
        viewDataBinding.layoutMain.setOnClickListener {
            beforeClicked = viewDataBinding.holder!!.id
            eventListener.onBackgroundClicked(viewDataBinding.holder!!.id)
            this.notifyDataSetChanged()
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderEditRoomBackgroundBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Background) {
            binding.holder = item

            if(beforeClicked == binding.holder!!.id) {
                binding.layoutMain.background =
                    binding.root.context.getDrawable(R.drawable.custom_transparents_radius04_line_yellow_thin)
                binding.roomImg.setColorFilter(Color.parseColor("#48303030"))
                binding.checkCircleImg.visibility = View.VISIBLE
            }
            else {
                binding.layoutMain.background =
                    binding.root.context.getDrawable(R.drawable.custom_transparents_radius04_line_transparent)
                binding.roomImg.setColorFilter(Color.parseColor("#00303030"))
                binding.checkCircleImg.visibility = View.INVISIBLE
            }
            binding.executePendingBindings()
        }
    }

    internal object BackgroundListItemDiffCallback : DiffUtil.ItemCallback<Background>() {
        override fun areItemsTheSame(oldItem: Background, newItem: Background) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Background, newItem: Background) =
            oldItem == newItem
    }
}