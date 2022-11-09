package com.thatnawfal.githubuser.presentation.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.data.model.UserModel
import com.thatnawfal.githubuser.databinding.FragmentHomeBinding
import com.thatnawfal.githubuser.presentation.ui.home.adapter.UserAdapter

class HomeFragment : Fragment() {

    companion object {
        const val EXTRA_KEY = "extra_key"
    }

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

        initRecyclerView()
    }

    @SuppressLint("Recycle")
    private fun addingDataToList() {
        val dataAvatar = resources.obtainTypedArray(R.array.avatar)
        val dataName = resources.getStringArray(R.array.name)
        val dataUsername = resources.getStringArray(R.array.username)
        val dataRepositoies = resources.getIntArray(R.array.repository)
        val dataFollowings = resources.getIntArray(R.array.following)
        val dataFollowers = resources.getIntArray(R.array.followers)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataCompany = resources.getStringArray(R.array.company)

        for (i in dataName.indices) {
            val dataUser = UserModel(
                dataAvatar.getResourceId(i, -1),
                dataCompany[i],
                dataFollowers[i],
                dataFollowings[i],
                dataLocation[i],
                dataName[i],
                dataRepositoies[i],
                dataUsername[i]
            )
            adapter.addItem(dataUser)
        }
    }

    private fun initRecyclerView() {
        binding.rvUserList.setHasFixedSize(true)
        binding.rvUserList.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        binding.rvUserList.adapter = adapter

        addingDataToList()

        adapter.itemClicked(object : UserAdapter.OnItemClickedCallback{
            override fun itemClicked(item: UserModel) {
                showSelectedItem(item)
            }
        })
    }

    private fun showSelectedItem(item: UserModel) {
        val mBundle = Bundle()
        mBundle.putParcelable(EXTRA_KEY, item)
        findNavController().navigate(R.id.action_homeFragment_to_detailUserFragment, mBundle)
    }

}