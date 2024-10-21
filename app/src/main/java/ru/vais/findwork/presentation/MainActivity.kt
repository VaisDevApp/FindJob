package ru.vais.findwork.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.launch
import ru.vais.core.di.BaseComponentHolder
import ru.vais.findwork.R
import ru.vais.findwork.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModelFactory = BaseComponentHolder.get().getViewModelFactory()

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = this.findNavController(R.id.nav_host_fragment)
        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)
        bottomNav.itemActiveIndicatorColor = null
        viewModel.loadData()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.screenStateFlow.collect {
                    if (it > 0) {
                        bottomNav.getOrCreateBadge(R.id.favorite).setNumber(it)
                    } else {
                        bottomNav.removeBadge(R.id.favorite)
                    }
                }

            }
        }
    }
}