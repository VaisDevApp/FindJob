package ru.vais.feature.favorite.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.vais.core.di.BaseComponentHolder
import ru.vais.feature.favorite.ui.presentation.adapter.BaseItem
import ru.vais.feature.favorite.ui.presentation.adapter.FavoriteAdapter
import ru.vais.feature.favorite.ui.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(), FavoriteAdapter.ClickListener {

    private val viewModelFactory = BaseComponentHolder.get().getViewModelFactory()
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
    }
    private val favoriteAdapter = FavoriteAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavorite.adapter = favoriteAdapter

        subscribeViewModel()

        viewModel.loadDate()
    }

    private fun subscribeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.stateScreenFlow.collect {
                    favoriteAdapter.update(it)
                }
            }
        }
    }

    override fun onClick(vacancy: BaseItem.VacancyUi) {
        viewModel.update(vacancy)
    }
}