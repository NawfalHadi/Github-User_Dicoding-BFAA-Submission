package com.thatnawfal.githubuser.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.databinding.FragmentFollowsBinding
import com.thatnawfal.githubuser.presentation.logic.UserViewModel
import com.thatnawfal.githubuser.presentation.ui.home.HomeFragment
import com.thatnawfal.githubuser.presentation.ui.home.adapter.UserAdapter

class FollowsFragment : Fragment() {

    companion object {
        var username: String? = ""
        const val SECTION_TABS = "section_tabs"
    }

    private lateinit var binding: FragmentFollowsBinding
    private val viewModel by viewModels<UserViewModel>()
    private val adapter : UserAdapter by lazy { UserAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shimmerList.startShimmer()

        viewModel.getListFollows(username.toString(), "followers")
        viewModel.getListFollows(username.toString(), "following")

        val followsTab = arguments?.getInt(SECTION_TABS, 0)
        initTabs(followsTab)
    }

    private fun initTabs(followsTab: Int?) {
        when(followsTab){
            1 -> {
                viewModel.listFollowers.observe(viewLifecycleOwner){
                    adapter.setItem(it)
                    binding.shimmerList.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }

                    initRecyclerView()
                }
            }
            2 -> {
                viewModel.listFollowing.observe(viewLifecycleOwner){
                    adapter.setItem(it)
                    binding.shimmerList.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }

                    initRecyclerView()
                }
            }
        }
    }

    private fun initRecyclerView() {
        with(binding){
            rvUserList.setHasFixedSize(true)
            rvUserList.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
            rvUserList.adapter = adapter
        }

        adapter.itemClicked(object : UserAdapter.OnItemClickedCallback{
            override fun itemClicked(username: String) {
                showSelectedItem(username)
            }
        })
    }
    

    private fun showSelectedItem(username: String) {
        findNavController().popBackStack()
        val mBundle = Bundle()
        mBundle.putString(HomeFragment.EXTRA_KEY, username)
        findNavController().navigate(R.id.action_homeFragment_to_detailUserFragment, mBundle)
    }
}