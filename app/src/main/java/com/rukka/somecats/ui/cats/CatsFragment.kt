package com.rukka.somecats.ui.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rukka.somecats.databinding.CatsFragmentBinding

class CatsFragment : Fragment() {

    private val viewModel: CatsViewModel by lazy {
        ViewModelProvider(this).get(CatsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CatsFragmentBinding.inflate(inflater)
        return binding.root
    }
}