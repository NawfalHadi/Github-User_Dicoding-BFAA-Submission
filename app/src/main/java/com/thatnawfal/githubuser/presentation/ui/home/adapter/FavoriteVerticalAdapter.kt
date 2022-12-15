package com.thatnawfal.githubuser.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity
import com.thatnawfal.githubuser.databinding.ItemListFavoriteVerticalBinding
import com.thatnawfal.githubuser.databinding.ItemListUserHorizontalBinding

class FavoriteVerticalAdapter : RecyclerView.Adapter<FavoriteVerticalAdapter.FavoriteViewHolder>() {
    private var diffCallback = object : DiffUtil.ItemCallback<FavoriteEntity>(){
        override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem.hashCode() == oldItem.hashCode()
        }
    }

    private lateinit var onFavItemClickedCallback: OnFavItemClickedCallback

    fun itemClicked(onItemClickedCallback: OnFavItemClickedCallback) {
        this.onFavItemClickedCallback = onItemClickedCallback
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : ArrayList<FavoriteEntity>) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val binding = ItemListFavoriteVerticalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bindingView(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class FavoriteViewHolder(
        private val binding : ItemListFavoriteVerticalBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindingView(data: FavoriteEntity?) {
            with(binding){
                itemTvNameVertical.text = data?.login
                itemTvUsernameVertical.text = StringBuilder("@").append(data?.login)
                itemIvUserVertical.load(data?.avatarUrl) {
                    placeholder(R.color.gray_80)
                }

                itemViewgroupVertical.setOnClickListener {
                    onFavItemClickedCallback.itemClicked(data?.login!!)
                }

                btnRemoved.setOnClickListener {
                    onFavItemClickedCallback.itemRemoved(data)
                }
            }

        }
    }

    interface OnFavItemClickedCallback {
        fun itemClicked(username: String)
        fun itemRemoved(data: FavoriteEntity?)
    }
}