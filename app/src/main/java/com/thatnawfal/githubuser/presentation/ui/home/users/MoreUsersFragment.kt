package com.thatnawfal.githubuser.presentation.ui.home.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.databinding.FragmentMoreUsersBinding
import com.thatnawfal.githubuser.presentation.logic.UserViewModel
import com.thatnawfal.githubuser.presentation.ui.home.HomeFragment
import com.thatnawfal.githubuser.presentation.ui.home.adapter.UserAdapter

class MoreUsersFragment : Fragment() {

    private lateinit var binding : FragmentMoreUsersBinding
    private val viewModel by viewModels<UserViewModel>()

    private val adapter: UserAdapter by lazy { UserAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreUsersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSync.setOnClickListener {
            viewModel.loadUsers(adapter.itemCount + 5)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        errorSnackbar()
        refreshUsers()
    }

    private fun refreshUsers() {
        with(binding){
            shimmerList.startShimmer()
            viewModel.defaultList.observe(viewLifecycleOwner){
                adapter.setItem(it)
                initRecyclerView()

                shimmerList.stopShimmer()
                rvMoreUsers.visibility = View.VISIBLE
                shimmerList.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            rvMoreUsers.setHasFixedSize(true)
            rvMoreUsers.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            rvMoreUsers.adapter = adapter
        }

        adapter.itemClicked(object : UserAdapter.OnItemClickedCallback {
            override fun itemClicked(username: String) {
                showSelectedItem(username)
            }
        })
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

    private fun showSelectedItem(username: String) {
        val mBundle = Bundle()
        mBundle.putString(HomeFragment.EXTRA_KEY, username)
        findNavController().popBackStack()
        findNavController().navigate(R.id.action_homeFragment_to_detailUserFragment, mBundle)
    }
}