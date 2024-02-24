package com.dracco.koinusergithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dracco.koinusergithub.api.model.response.Repository
import com.dracco.koinusergithub.databinding.ItemRepositoryBinding

class RepositoryAdapter(
    private val onClick: (Repository) -> Unit,
    private val onShareClick: (Repository) -> Unit
): RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private var list: AsyncListDiffer<Repository> = AsyncListDiffer(this, DiffCallBack)

    fun addAll(userList: List<Repository>){
        list.submitList(userList)
    }

    object DiffCallBack : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(
            oldItem: Repository,
            newItem: Repository
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Repository,
            newItem: Repository
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(
        private val binding: ItemRepositoryBinding
    ): RecyclerView.ViewHolder(binding.root) {


        fun bind(repository: Repository){

            binding.tvRepository.text = repository.name

            binding.ivShare.setOnClickListener{
                onShareClick(repository)
            }

            binding.root.setOnClickListener {
                onClick(repository)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRepositoryBinding.inflate(
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