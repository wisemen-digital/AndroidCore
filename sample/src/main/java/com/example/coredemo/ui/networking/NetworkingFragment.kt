package com.example.coredemo.ui.networking

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import be.appwise.core.extensions.fragment.showSnackBar
import be.appwise.core.extensions.view.setupRecyclerView
import be.appwise.core.ui.base.BaseBindingVMFragment
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentNetworkingBinding

class NetworkingFragment : BaseBindingVMFragment<FragmentNetworkingBinding>() {

    override fun getLayout() = R.layout.fragment_networking
    override val mViewModel: NetworkingViewModel by viewModels()

    private val pokemonAdapter by lazy {
        PokemonAdapter {
            when (it.id) {
                in arrayOf("1", "2", "3") -> {
                    mViewModel.fetchSpecificPokemonOldWay(it.id)
                }
                in arrayOf("4", "5", "6") -> {
                    mViewModel.fetchSpecificPokemon(it.id)
                }
                in arrayOf("7", "8", "9") -> {
                    mViewModel.fetchSpecificPokemonWithDifferentHandler(it.id)
                }
                in arrayOf("10", "11", "12") -> {
                    mViewModel.fetchSpecificPokemonWithNewWrapper(it.id)
                }
                else -> {
                    mViewModel.fetchSpecificPokemonFactory(it.id)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel

        initViews()
        initObservers()
    }

    private fun initViews() {
        mBinding.rvItems.let {
            it.setupRecyclerView()
            it.adapter = pokemonAdapter
        }
    }

    private fun initObservers() {
        mViewModel.pokemons.observe(viewLifecycleOwner) {
            pokemonAdapter.submitList(it)
        }

        mViewModel.pokemon.observe(viewLifecycleOwner) {
            showSnackBar(it.parseSnackbarMessage)
        }
    }
}