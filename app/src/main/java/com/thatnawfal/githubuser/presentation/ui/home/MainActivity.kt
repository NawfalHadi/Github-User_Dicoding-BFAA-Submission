package com.thatnawfal.githubuser.presentation.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.data.model.UserModel
import com.thatnawfal.githubuser.databinding.ActivityMainBinding
import com.thatnawfal.githubuser.presentation.ui.home.adapter.UserAdapter
import com.thatnawfal.githubuser.presentation.ui.profile.DetailUserActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY = "extra_key"
    }

    private lateinit var binding : ActivityMainBinding
    private val adapter : UserAdapter by lazy {
        UserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    @SuppressLint("Recycle")
    private fun addingDataToList() {
        val dataAvatar = resources.obtainTypedArray(R.array.avatar)
        val dataName = resources.getStringArray(R.array.name)
        val dataUsername = resources.getStringArray(R.array.username)
        val dataRepositoies = resources.getIntArray(R.array.repository)
        val dataFollowings = resources.getIntArray(R.array.following)
        val dataFollowers = resources.getIntArray(R.array.followers)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataCompany = resources.getStringArray(R.array.company)

        for (i in dataName.indices) {
            val dataUser = UserModel(
                dataAvatar.getResourceId(i, -1),
                dataCompany[i],
                dataFollowers[i],
                dataFollowings[i],
                dataLocation[i],
                dataName[i],
                dataRepositoies[i],
                dataUsername[i]
            )
            adapter.addItem(dataUser)
        }

    }

    private fun initRecyclerView() {
        binding.rvUserList.setHasFixedSize(true)
        binding.rvUserList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        binding.rvUserList.adapter = adapter

        addingDataToList()

        adapter.itemClicked(object : UserAdapter.OnItemClickedCallback{
            override fun itemClicked(item: UserModel) {
                showSelectedItem(item)
            }
        })
    }

    private fun showSelectedItem(item: UserModel) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(EXTRA_KEY, item)
        startActivity(intent)
    }


}