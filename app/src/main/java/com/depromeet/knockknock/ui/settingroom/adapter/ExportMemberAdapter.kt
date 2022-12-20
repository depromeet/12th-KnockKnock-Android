package com.depromeet.knockknock.ui.settingroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderExportMemberBinding
import com.depromeet.knockknock.ui.friendlist.model.User
import com.depromeet.knockknock.ui.settingroom.SettingRoomActionHandler

class ExportMemberAdapter(
    val eventListener: SettingRoomActionHandler
) : ListAdapter<User, ExportMemberAdapter.ViewHolder>(FriendListItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderExportMemberBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_export_member,
            parent,
            false
        )
        viewDataBinding.exportBtn.setOnClickListener {
            eventListener.onExportClicked(viewDataBinding.holder!!.userIdx)
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderExportMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FriendListItemDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.userIdx == newItem.userIdx

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
    }
}