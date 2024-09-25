package ru.vais.feature.search.ui.presentation.full

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.vais.feature.search.ui.presentation.adapter.search.SearchAdapter
import ru.vais.core.di.BaseComponentHolder
import ru.vais.feature.search.ui.R
import ru.vais.feature.search.ui.databinding.FragmentSearchFullBinding
import ru.vais.feature.search.ui.presentation.adapter.search.BaseItem

class SearchFullFragment : Fragment() {

    private val viewModelFactory = BaseComponentHolder.get().getViewModelFactory()
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchFullViewModel::class.java]
    }

    private lateinit var binding: FragmentSearchFullBinding
    private val searchAdapter = SearchAdapter(object : SearchAdapter.OnClickListener {
        override fun onClick() {
            //stub
        }

        override fun onClickChangeFavorite(vacancy: BaseItem.VacancyUi) {
            viewModel.changeFavorite(vacancy)
        }

        override fun onClickToVacancyCard(vacancy: BaseItem.VacancyUi) {
            findNavController().navigate(R.id.to_vacancy_detail)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchFullBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSearch.adapter = searchAdapter

        binding.icFindView.setOnClickListener { findNavController().popBackStack() }

        subscribeViewModel()
        viewModel.loadData()
    }
    private fun subscribeViewModel(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.flowStateScreen.collect {
                    when (it) {
                        is SearchFullViewModel.StateScreen.Content -> {
                            binding.progressbar.isVisible = false
                            binding.buttonRepeatLoad.isVisible = false
                            searchAdapter.submitList(it.baseItemList)
                        }

                        is SearchFullViewModel.StateScreen.Error -> {
                            binding.progressbar.isVisible = false
                            binding.buttonRepeatLoad.isVisible = true
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.network_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is SearchFullViewModel.StateScreen.Progress -> {
                            binding.progressbar.isVisible = true
                            binding.buttonRepeatLoad.isVisible = false
                        }
                    }
                }
            }
        }
    }
}