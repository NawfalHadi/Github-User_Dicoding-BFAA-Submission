package com.thatnawfal.githubuser.presentation.ui.home.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.databinding.FragmentFavoriteUsersBinding
import com.thatnawfal.githubuser.di.ServiceLocator
import com.thatnawfal.githubuser.presentation.logic.FavoriteViewModel
import com.thatnawfal.githubuser.utils.viewModelFactory

class FavoriteUsersFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteUsersBinding
    private val viewModel by viewModelFactory {
        FavoriteViewModel(ServiceLocator.provideFavoriteRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteUsersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}