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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity
import com.thatnawfal.githubuser.data.model.response.DetailUsersModel
import com.thatnawfal.githubuser.databinding.FragmentDetailUserBinding
import com.thatnawfal.githubuser.presentation.logic.FavoriteViewModel
import com.thatnawfal.githubuser.presentation.logic.UserViewModel
import com.thatnawfal.githubuser.presentation.ui.home.HomeFragment
import com.thatnawfal.githubuser.presentation.ui.profile.adapter.FollowsPagerAdapter
import com.thatnawfal.githubuser.utils.viewModelFactory

class DetailUserFragment : Fragment() {

    private lateinit var binding: FragmentDetailUserBinding

    private val viewModel by viewModels<UserViewModel>()
    private val favoriteViewModel by viewModelFactory {
        FavoriteViewModel(activity?.application!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabsPagerLoading()

        val username = arguments?.getString(HomeFragment.EXTRA_KEY)
        viewModel.detailUser(username!!)

        binding.headerDetailUser.shimmerDetailUserHeader.startShimmer()
        viewModel.user.observe(viewLifecycleOwner) {
            bindingView(it)
            stopShimmer()
        }

        binding.backButtonDetail.setOnClickListener {
            findNavController().popBackStack()
        }

        errorSnackbar()
    }

    private fun errorSnackbar() {
        viewModel.snackbarMsg.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { msg ->
                activity?.window?.decorView?.rootView?.let { rootView ->
                    Snackbar.make(
                        rootView,
                        msg,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun tabsPagerLoading() {
        val tabTitles = arrayOf(
            "(0) ${resources.getString(R.string.followers)}",
            "(0) ${resources.getString(R.string.following)}"
        )

        with(binding.followsDetailUser) {
            viewpagerFollows.adapter = parentFragment?.let { FollowsPagerAdapter(it) }
            TabLayoutMediator(tabsFollow, viewpagerFollows) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    private fun stopShimmer() {
        with(binding) {
            with(headerDetailUser) {
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
        val tabTitles = arrayOf(
            "(${dataUser.followers}) ${resources.getString(R.string.followers)}",
            "(${dataUser.following}) ${resources.getString(R.string.following)}"
        )
        FollowsFragment.username = dataUser.login

        with(binding) {

            detailFbToFavorite.apply {
                favoriteViewModel.checkFavorite(dataUser.id!!)
                visibility = View.VISIBLE

                favoriteViewModel.isFavorited.observe(viewLifecycleOwner){
                    if (it){
                        detailFbToFavorite.setImageResource(R.drawable.ic_favorited)
                    } else {
                        detailFbToFavorite.setImageResource(R.drawable.ic_unfavorited)
                        setOnClickListener {
                            val entity = FavoriteEntity(
                                dataUser.id,
                                dataUser.login!!,
                                dataUser.avatarUrl,
                                true
                            )
                            favoriteViewModel.addFavorite(entity)
                        }
                    }
                }
            }
            detailFbToGithub.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    openGithubInBrowser(dataUser.login)
                }
            }

            with(followsDetailUser) {
                viewpagerFollows.adapter = parentFragment?.let { FollowsPagerAdapter(it) }
                TabLayoutMediator(tabsFollow, viewpagerFollows) { tab, position ->
                    tab.text = tabTitles[position]
                }.attach()
            }

            with(headerDetailUser) {
                ivDetailAvatar.load(dataUser.avatarUrl)
                ivDetailAvatar.visibility = View.VISIBLE

                tvDetailNames.text = dataUser.name
                tvDetailUsername.text = StringBuilder("@").append(dataUser.login)
                repoUserDetail.tvUserRepo.text = dataUser.publicRepos.toString()
                tvDetailLocation.text = dataUser.location
                tvDetailCompany.text = dataUser.company
            }
        }
    }

}
