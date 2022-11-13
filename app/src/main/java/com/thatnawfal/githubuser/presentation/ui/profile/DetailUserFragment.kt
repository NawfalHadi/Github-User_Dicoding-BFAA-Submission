package com.thatnawfal.githubuser.presentation.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.thatnawfal.githubuser.data.model.response.DetailUsersModel
import com.thatnawfal.githubuser.databinding.FragmentDetailUserBinding
import com.thatnawfal.githubuser.presentation.logic.UserViewModel
import com.thatnawfal.githubuser.presentation.ui.home.HomeFragment

class DetailUserFragment : Fragment() {

    private lateinit var binding: FragmentDetailUserBinding
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(HomeFragment.EXTRA_KEY)

        viewModel.detailUser(username?:"mojombo")

        binding.headerDetailUser.shimmerDetailUserHeader.startShimmer()

        viewModel.user.observe(viewLifecycleOwner){
            bindingView(it)
            stopShimmer()
        }

        binding.backButtonDetail.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun stopShimmer() {
        with(binding){
            with(headerDetailUser){
                shimmerDetailUserHeader.stopShimmer()
                shimmerDetailUserHeader.visibility = View.GONE
            }
        }
    }

    private fun openGithubInBrowser(username: String?) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("https://github.com/${username}")
        startActivity(i)
    }

    @SuppressLint("SetTextI18n")
    private fun bindingView(dataUser: DetailUsersModel) {

        with(binding){

            detailFbToGithub.apply {
                visibility = View.VISIBLE
                setOnClickListener{
                    openGithubInBrowser(dataUser.login)
                }
            }

            with(followsDetailUser){
                tvFollowers.text = dataUser.followers.toString()
                tvFollowings.text = dataUser.following.toString()
            }

            with(headerDetailUser){
                ivDetailAvatar.load(dataUser.avatarUrl)
                ivDetailAvatar.visibility = View.VISIBLE

                tvDetailNames.text = dataUser.name
                tvDetailUsername.text = "@${dataUser.login}"
                repoUserDetail.tvUserRepo.text = dataUser.publicRepos.toString()
                tvDetailLocation.text = dataUser.location
                tvDetailCompany.text = dataUser.company
            }
        }
    }

}
