package com.example.myapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.databinding.FragmentMainBinding
import com.example.myapp.view.adapter.RickAndMortyAdapter
import com.example.myapp.viewmodel.RickAndMortyViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: RickAndMortyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recycleListView = binding.rvCharacteres
        recycleListView.layoutManager = LinearLayoutManager(context)

        viewModel.characters.observe(viewLifecycleOwner, Observer { list ->
            recycleListView.adapter = RickAndMortyAdapter(list) {
                findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
            }
        })

        viewModel.getCharacters()
    }

    fun loadFromApi() {
        viewModel.loadFromApi()
    }

}