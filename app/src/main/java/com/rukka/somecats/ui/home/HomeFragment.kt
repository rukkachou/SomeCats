package com.rukka.somecats.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rukka.somecats.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HomeFragmentBinding.inflate(inflater)
        val adapter = HomeAdapter()
        binding.homeRecyclerView.adapter = adapter
        viewModel.newCatsList.observe(viewLifecycleOwner, Observer {
            adapter.submitCustomizedList(it)
            Log.i("MeowMeow", "${it.size}")
        })
        return binding.root
    }
}