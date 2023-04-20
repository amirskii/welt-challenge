package com.example.weltchallenge.features.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.weltchallenge.data.models.User
import com.example.weltchallenge.databinding.ItemUserBinding
import com.example.weltchallenge.features.base.BaseViewHolder

internal class UserAdapter(
    private val onItemClicked: (User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(
    DiffCallback
) {

    companion object {
        private object DiffCallback : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        LayoutInflater.from(parent.context)
            .let { ItemUserBinding.inflate(it, parent, false) }
            .let {
                UserViewHolder(it, onItemClicked)
            }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    internal class UserViewHolder(
        binding: ItemUserBinding,
        private val onItemClicked: (User) -> Unit,
    ) : BaseViewHolder<User, ItemUserBinding>(binding) {

        override fun bind(item: User, position: Int) {
            with(binding) {
                userName.text = item.login

                placeholderTextView.isVisible = item.imageUrl.isEmpty()
                placeholderTextView.text = item.imagePlaceholder
//                userImage.loadRoundImage(item.imageUrl, onError = {
//                    placeholderTextView.isVisible = true
//                    placeholderTextView.text = item.imagePlaceholder
//                })

                root.setOnClickListener {
                    onItemClicked(item)
                }
            }
        }
    }
}
