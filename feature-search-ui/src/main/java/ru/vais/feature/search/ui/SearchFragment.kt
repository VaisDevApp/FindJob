package ru.vais.feature.search.ui

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
import layout.SearchAdapter
import ru.vais.core.di.BaseComponentHolder
import ru.vais.feature.search.ui.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private val viewModelFactory = BaseComponentHolder.get().getViewModelFactory()

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
    }

    lateinit var binding: FragmentSearchBinding
    val searchAdapter = SearchAdapter(object : SearchAdapter.OnClickListener {
        override fun onClick() {
            viewModel.getAllVacancyInFragment()
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
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSearch.adapter = searchAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.flowStateScreen.collect {
                    when (it) {
                        is SearchViewModel.StateScreen.Content -> {
                            binding.progressbar.isVisible = false
                            binding.buttonRepeatLoad.isVisible = false
                            searchAdapter.update(it.baseItemList)
                        }

                        is SearchViewModel.StateScreen.Error -> {
                            binding.progressbar.isVisible = false
                            binding.buttonRepeatLoad.isVisible = true
                            Toast.makeText(
                                requireContext(),
                                "Проверьте подключение к интернету",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is SearchViewModel.StateScreen.ProgressBar -> {
                            binding.progressbar.isVisible = true
                            binding.buttonRepeatLoad.isVisible = false
                        }
                    }
                }
            }
        }
        binding.buttonRepeatLoad.setOnClickListener {
            viewModel.loadData()
        }
        viewModel.loadData()
    }
}