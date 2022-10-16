package com.thatnawfal.githubuser.presentation.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.thatnawfal.githubuser.data.model.UserModel
import com.thatnawfal.githubuser.databinding.ActivityDetailUserBinding
import com.thatnawfal.githubuser.presentation.ui.home.MainActivity

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerDetailUser.backButtonDetail.setOnClickListener {
            finish()
        }

        val dataUser = intent.getParcelableExtra(MainActivity.EXTRA_KEY) as? UserModel
        dataUser?.let { bindingView(it) }
    }

    private fun bindingView(dataUser: UserModel) {
        with(binding){
            ivDetailAvatar.load(dataUser.avatar)
            tvDetailNames.text = dataUser.name
            tvDetailUsername.text = dataUser.username

            with(followsDetailUser){
                tvFollowers.text = dataUser.follower.toString()
                tvFollowings.text = dataUser.following.toString()
            }

            with(headerDetailUser){
                repoUserDetail.tvUserRepo.text = dataUser.repository.toString()
                tvDetailLocation.text = dataUser.location
                tvDetailCompany.text = dataUser.company
            }
        }
    }
}