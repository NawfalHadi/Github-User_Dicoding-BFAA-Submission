package com.thatnawfal.githubuser.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thatnawfal.githubuser.data.model.UserModel
import com.thatnawfal.githubuser.databinding.ItemListUserVerticalBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val listData : MutableList<UserModel> = mutableListOf()
    private lateinit var onItemClickedCallback: OnItemClickedCallback

    fun addItem(item: UserModel){
        this.listData.add(item)
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

        fun bindingView(item: UserModel) {
            with(binding){
                itemTvNameVertical.text = item.name
                itemTvUsernameVertical.text = item.username
                itemIvUserVertical.load(item.avatar)

                itemViewgroupVertical.setOnClickListener{
                    onItemClickedCallback.itemClicked(item)
                }
            }
        }
    }

    interface OnItemClickedCallback {
        fun itemClicked(item: UserModel)
    }
}