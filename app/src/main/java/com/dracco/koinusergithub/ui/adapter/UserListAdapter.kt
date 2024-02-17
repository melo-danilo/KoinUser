package com.dracco.koinusergithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.databinding.ItemUserListBinding

class UserListAdapter(
    private val onClick: (User) -> Unit
): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private var list: AsyncListDiffer<User> = AsyncListDiffer(this, DiffCallBack)

    fun addAll(userList: List<User>){
        list.submitList(userList)
    }

    object DiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(
        private val binding: ItemUserListBinding
    ): RecyclerView.ViewHolder(binding.root) {


        fun bind(user: User){

            binding.image.load(user.avatarUrl)
            binding.name.text = user.login
            binding.github.text = user.htmlUrl

            binding.root.setOnClickListener {
                onClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.currentList[position])
    }
}