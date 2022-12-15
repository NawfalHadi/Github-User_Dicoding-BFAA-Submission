package com.thatnawfal.githubuser.presentation.ui.home.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity
import com.thatnawfal.githubuser.databinding.FragmentFavoriteUsersBinding
import com.thatnawfal.githubuser.di.ServiceLocator
import com.thatnawfal.githubuser.presentation.logic.FavoriteViewModel
import com.thatnawfal.githubuser.presentation.ui.home.HomeFragment
import com.thatnawfal.githubuser.presentation.ui.home.adapter.FavoriteAdapter
import com.thatnawfal.githubuser.presentation.ui.home.adapter.FavoriteVerticalAdapter
import com.thatnawfal.githubuser.utils.viewModelFactory

class FavoriteUsersFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteUsersBinding
    private val viewModel by viewModelFactory {
        FavoriteViewModel(ServiceLocator.provideFavoriteRepository(requireContext()))
    }

    private val favAdapter : FavoriteVerticalAdapter by lazy { FavoriteVerticalAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteUsersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        viewModel.getAllFavorites().observe(viewLifecycleOwner){
            with(binding){
                shimmerList.startShimmer()
                if (it.isNotEmpty()){
                    favAdapter.setItem(it as ArrayList<FavoriteEntity>)
                    initFavRecyclerView()

                    binding.shimmerList.visibility = View.INVISIBLE
                    binding.rvFavoriteUsers.visibility = View.VISIBLE
                    binding.textNullFavoriteuser.visibility = View.INVISIBLE
                } else {
                    binding.shimmerList.visibility = View.INVISIBLE
                    binding.rvFavoriteUsers.visibility = View.INVISIBLE
                    binding.textNullFavoriteuser.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initFavRecyclerView() {
        with(binding) {
            rvFavoriteUsers.setHasFixedSize(true)
            rvFavoriteUsers.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            rvFavoriteUsers.adapter = favAdapter
        }

        favAdapter.itemClicked(object : FavoriteVerticalAdapter.OnFavItemClickedCallback {
            override fun itemClicked(username: String) {
                showSelectedItem(username)
            }

            override fun itemRemoved(data: FavoriteEntity?) {
                viewModel.removeFavorite(data!!)
            }
        })
    }

    private fun showSelectedItem(username: String) {
        val mBundle = Bundle()
        mBundle.putString(HomeFragment.EXTRA_KEY, username)
        findNavController().popBackStack()
        findNavController().navigate(R.id.action_homeFragment_to_detailUserFragment, mBundle)
    }

}