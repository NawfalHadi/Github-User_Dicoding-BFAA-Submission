package com.thatnawfal.githubuser.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.thatnawfal.githubuser.databinding.FragmentHomeBinding
import com.thatnawfal.githubuser.presentation.logic.UserViewModel
import com.thatnawfal.githubuser.presentation.ui.home.adapter.UserAdapter

class HomeFragment : Fragment() {

    companion object {
        const val EXTRA_KEY = "extra_key"
    }

    private val viewModel by viewModels<UserViewModel>()
    private lateinit var binding: FragmentHomeBinding

    private val adapter : UserAdapter by lazy {
        UserAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.layoutHomeContent.pbLoadUsers.isVisible = it
        }

        refreshListUsers()
        searchFunction()
    }

    private fun searchFunction() {
        with(binding.layoutHomeHeader){
            searchHomeHeader.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchUsers(query?:"")
                    refreshListUsers()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })
        }
    }

    private fun initRecyclerView() {
        with(binding.layoutHomeContent){
            rvUserList.setHasFixedSize(true)
            rvUserList.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
            rvUserList.adapter = adapter
        }

//        adapter.itemClicked(object : UserAdapter.OnItemClickedCallback{
//            override fun itemClicked(item: UserModel) {
//                showSelectedItem(item)
//            }
//        })
    }

    private fun refreshListUsers() {
        viewModel.listUsers.observe(viewLifecycleOwner){
            adapter.setItem(it)
            initRecyclerView()
        }
    }

//    private fun showSelectedItem(item: UsersModel) {
//        val mBundle = Bundle()
//        mBundle.putParcelable(EXTRA_KEY, item)
//        findNavController().navigate(R.id.action_homeFragment_to_detailUserFragment, mBundle)
//    }

}