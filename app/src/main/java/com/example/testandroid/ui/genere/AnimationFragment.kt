package com.example.testandroid.ui.genere

import com.example.testandroid.ui.top.TopFragmentDirections
import com.example.testandroid.ui.top.TopMovieItemAdapter
import com.example.testandroid.ui.top.TopViewModel


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testandroid.R
import com.example.testandroid.data.entities.MovieEntity
import com.example.testandroid.data.model.Movie
import com.example.testandroid.data.model.ResourceStatus
import com.example.testandroid.databinding.FragmentAnimationBinding
import com.example.testandroid.databinding.FragmentTopBinding
import com.example.testandroid.ui.popular.PopularViewModel
import com.example.testandroid.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AnimationFragment : Fragment(), AnimationMovieItemAdapter.OnMovieClickListener {

    private var _binding: FragmentAnimationBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AnimationViewModel by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var animationMovieItemAdapter: AnimationMovieItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAnimationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovies3.layoutManager = LinearLayoutManager(context)

        viewModel.fetchAnimationMovies.observe(viewLifecycleOwner, Observer {
            when (it.resourceStatus) {
                ResourceStatus.LOADING -> {
                    Log.e("fetchTopMovies", "Loading")
                }
                ResourceStatus.SUCCESS  -> {
                    Log.e("fetchTopMovies", "Success")
                    animationMovieItemAdapter = AnimationMovieItemAdapter(it.data!!, this@AnimationFragment)
                    binding.rvMovies3.adapter = animationMovieItemAdapter
                }
                ResourceStatus.ERROR -> {
                    Log.e("fetchAnimationMovies", "Failure: ${it.message} ")
                    Toast.makeText(requireContext(), "Failure: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(movieEntity: MovieEntity) {
        val action = TopFragmentDirections.actionTopFragmentToDetailFragment(movieEntity)
        findNavController().navigate(action)
    }
}