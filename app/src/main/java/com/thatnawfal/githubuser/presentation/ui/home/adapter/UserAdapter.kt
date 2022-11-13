package com.thatnawfal.githubuser.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thatnawfal.githubuser.data.model.response.UsersModel
import com.thatnawfal.githubuser.databinding.ItemListUserVerticalBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var listData : MutableList<UsersModel> = mutableListOf()
    private lateinit var onItemClickedCallback: OnItemClickedCallback

    fun setItem(list: List<UsersModel>) {
        listData.clear()
        listData.addAll(list)
    }

    fun clearItem(){
        listData.clear()
    }

    fun itemClicked(onItemClickedCallback: OnItemClickedCallback){
        this.onItemClickedCallback = onItemClickedCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding = ItemListUserVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val item = listData[position]
        holder.bindingView(item)
    }

    override fun getItemCount(): Int = listData.size

    inner class UserViewHolder(
        private val binding: ItemListUserVerticalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindingView(item: UsersModel) {
            with(binding){
                itemTvNameVertical.text = item.login
                // here change by suggestion from the reviewer before
                itemTvUsernameVertical.text = "@${item.login}"
                itemIvUserVertical.load(item.avatarUrl)

                itemViewgroupVertical.setOnClickListener{
                    onItemClickedCallback.itemClicked(item.login!!)
                }
            }
        }
    }

    interface OnItemClickedCallback {
        fun itemClicked(username: String)
    }
}