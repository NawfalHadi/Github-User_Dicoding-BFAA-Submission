package com.thatnawfal.githubuser.presentation.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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

        val dataUser = intent.getParcelableExtra(MainActivity.EXTRA_KEY) as? UserModel
        dataUser?.let { bindingView(it) }

        binding.backButtonDetail.setOnClickListener {
            finish()
        }

        binding.detailFbToGithub.setOnClickListener{
            openGithubInBrowser(dataUser?.username)
        }
    }

    private fun openGithubInBrowser(username: String?) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("https://github.com/${username}")
        startActivity(i)
    }


    @SuppressLint("SetTextI18n")
    private fun bindingView(dataUser: UserModel) {
        with(binding){
            with(followsDetailUser){
                tvFollowers.text = dataUser.follower.toString()
                tvFollowings.text = dataUser.following.toString()
            }

            with(headerDetailUser){
                ivDetailAvatar.load(dataUser.avatar)
                tvDetailNames.text = dataUser.name
                tvDetailUsername.text = "@${dataUser.username}"
                repoUserDetail.tvUserRepo.text = dataUser.repository.toString()
                tvDetailLocation.text = dataUser.location
                tvDetailCompany.text = dataUser.company
            }
        }
    }
}