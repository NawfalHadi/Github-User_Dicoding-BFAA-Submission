package com.thatnawfal.githubuser.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity
import com.thatnawfal.githubuser.databinding.FragmentHomeBinding
import com.thatnawfal.githubuser.di.ServiceLocator
import com.thatnawfal.githubuser.presentation.logic.FavoriteViewModel
import com.thatnawfal.githubuser.presentation.logic.UserViewModel
import com.thatnawfal.githubuser.presentation.ui.home.adapter.FavoriteAdapter
import com.thatnawfal.githubuser.presentation.ui.home.adapter.UserAdapter
import com.thatnawfal.githubuser.presentation.ui.setting.SettingFragment
import com.thatnawfal.githubuser.utils.viewModelFactory

class HomeFragment : Fragment() {

    companion object {
        const val EXTRA_KEY = "extra_key"
    }

    private val viewModel by viewModels<UserViewModel>()
    private val favoriteViewModel by viewModelFactory {
        FavoriteViewModel(ServiceLocator.provideFavoriteRepository(requireContext()))
    }

    private lateinit var binding: FragmentHomeBinding

    private val adapter: UserAdapter by lazy { UserAdapter() }
    private val favAdapter : FavoriteAdapter by lazy { FavoriteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSettings.setOnClickListener {
            showSettings()
        }

        errorSnackbar()
        refreshListUsers()
        searchFunction()
    }

    private fun showSettings() {
        val currentDialog = parentFragmentManager.findFragmentByTag(SettingFragment::class.java.simpleName)
        currentDialog ?: SettingFragment().show(parentFragmentManager, SettingFragment::class.java.simpleName)

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

    private fun searchFunction() {
        with(binding.layoutHomeHeader) {
            searchHomeHeader.setIconifiedByDefault(false)
            searchHomeHeader.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchUsers(query ?: "")
                    binding.layoutHomeContent.rvUserList.visibility = View.GONE
                    binding.layoutHomeContent.shimmerList.visibility = View.VISIBLE
                    binding.layoutHomeContent.shimmerList.startShimmer()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.length == 0) {
                        binding.layoutHomeContent.rvUserList.visibility = View.GONE
                        binding.layoutHomeContent.shimmerList.visibility = View.VISIBLE
                        binding.layoutHomeContent.shimmerList.startShimmer()

                        viewModel.emptySearchField()
                    }
                    return true
                }

            })
        }
    }

    private fun initRecyclerView() {
        with(binding.layoutHomeContent) {
            rvUserList.setHasFixedSize(true)
            rvUserList.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            rvUserList.adapter = adapter
        }

        adapter.itemClicked(object : UserAdapter.OnItemClickedCallback {
            override fun itemClicked(username: String) {
                showSelectedItem(username)
            }
        })
    }

    private fun initFavRecyclerView() {
        with(binding.layoutHomeContent) {
            rvFavoriteList.setHasFixedSize(true)
            rvFavoriteList.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            rvFavoriteList.adapter = favAdapter
        }
    }

    private fun refreshListUsers() {
        binding.layoutHomeContent.shimmerList.startShimmer()
        binding.layoutHomeContent.shimmerListHorizontal.startShimmer()

        binding.layoutHomeContent.rvUserList.visibility = View.GONE
        viewModel.defaultList.observe(viewLifecycleOwner) {
            adapter.setItem(it)
            initRecyclerView()

            binding.layoutHomeContent.shimmerList.stopShimmer()
            binding.layoutHomeContent.shimmerList.visibility = View.GONE
            binding.layoutHomeContent.rvUserList.visibility = View.VISIBLE
        }

        viewModel.listUsers.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setItem(it)
                initRecyclerView()

                binding.layoutHomeContent.shimmerList.stopShimmer()
                binding.layoutHomeContent.shimmerList.visibility = View.GONE
                binding.layoutHomeContent.rvUserList.visibility = View.VISIBLE
            } else {
                viewModel.loadUsers()
            }
        }

        favoriteViewModel.getAllFavorites().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                favAdapter.setItem(it as ArrayList<FavoriteEntity>)
                initFavRecyclerView()

                binding.layoutHomeContent.shimmerListHorizontal.stopShimmer()
                binding.layoutHomeContent.shimmerListHorizontal.visibility = View.INVISIBLE
                binding.layoutHomeContent.rvFavoriteList.visibility = View.VISIBLE
            } else {
                binding.layoutHomeContent.shimmerListHorizontal.stopShimmer()
                binding.layoutHomeContent.shimmerListHorizontal.visibility = View.INVISIBLE
                binding.layoutHomeContent.textNullFavoriteuser.visibility = View.VISIBLE
            }
        }
    }



    private fun showSelectedItem(username: String) {
        val mBundle = Bundle()
        mBundle.putString(EXTRA_KEY, username)
        findNavController().navigate(R.id.action_homeFragment_to_detailUserFragment, mBundle)
    }

}