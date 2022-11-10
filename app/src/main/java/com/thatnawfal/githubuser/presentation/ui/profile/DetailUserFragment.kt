package com.thatnawfal.githubuser.presentation.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.thatnawfal.githubuser.data.model.response.UsersModel
import com.thatnawfal.githubuser.databinding.FragmentDetailUserBinding
import com.thatnawfal.githubuser.presentation.ui.home.HomeFragment

class DetailUserFragment : Fragment() {

    private lateinit var binding: FragmentDetailUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataUser = arguments?.getParcelable(HomeFragment.EXTRA_KEY) as? UsersModel
        dataUser?.let { bindingView(it) }

        binding.backButtonDetail.setOnClickListener {
            findNavController().popBackStack()
        }

//        binding.detailFbToGithub.setOnClickListener{
//            openGithubInBrowser(dataUser?.username)
//        }
    }

    private fun openGithubInBrowser(username: String?) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("https://github.com/${username}")
        startActivity(i)
    }

    @SuppressLint("SetTextI18n")
    private fun bindingView(dataUser: UsersModel) {
//        with(binding){
//            with(followsDetailUser){
//                tvFollowers.text = dataUser.follower.toString()
//                tvFollowings.text = dataUser.following.toString()
//            }
//
//            with(headerDetailUser){
//                ivDetailAvatar.load(dataUser.avatar)
//                tvDetailNames.text = dataUser.name
//                tvDetailUsername.text = "@${dataUser.username}"
//                repoUserDetail.tvUserRepo.text = dataUser.repository.toString()
//                tvDetailLocation.text = dataUser.location
//                tvDetailCompany.text = dataUser.company
//            }
//        }
    }

}
