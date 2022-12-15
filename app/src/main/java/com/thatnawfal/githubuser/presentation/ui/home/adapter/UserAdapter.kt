package com.thatnawfal.githubuser.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity
import com.thatnawfal.githubuser.data.model.response.UsersModel
import com.thatnawfal.githubuser.databinding.ItemListUserVerticalBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var onItemClickedCallback: OnItemClickedCallback

    private var diffCallback = object : DiffUtil.ItemCallback<UsersModel>(){
        override fun areItemsTheSame(oldItem: UsersModel, newItem: UsersModel): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: UsersModel, newItem: UsersModel): Boolean {
            return oldItem.hashCode() == oldItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<UsersModel>) = differ.submitList(value)

    fun itemClicked(onItemClickedCallback: OnItemClickedCallback) {
        this.onItemClickedCallback = onItemClickedCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding =
            ItemListUserVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingView(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class UserViewHolder(
        private val binding: ItemListUserVerticalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindingView(item: UsersModel) {
            with(binding) {
                itemTvNameVertical.text = item.login
                itemTvUsernameVertical.text = StringBuilder("@").append(item.login)
                itemIvUserVertical.load(item.avatarUrl) {
                    placeholder(R.color.gray_80)
                }

                itemViewgroupVertical.setOnClickListener {
                    onItemClickedCallback.itemClicked(item.login!!)
                }
            }
        }
    }

    interface OnItemClickedCallback {
        fun itemClicked(username: String)
    }
}