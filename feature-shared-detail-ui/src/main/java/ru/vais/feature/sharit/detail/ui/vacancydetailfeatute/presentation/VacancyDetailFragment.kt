package ru.vais.feature.sharit.detail.ui.vacancydetailfeatute.presentation

import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.vais.core.di.BaseComponentHolder
import ru.vais.feature.sharit.detail.ui.R
import ru.vais.feature.sharit.detail.ui.databinding.FragmentVacancyDetailBinding
import ru.vais.feature.vacancy.data.domain.entity.Vacancy


class VacancyDetailFragment : Fragment(R.layout.fragment_vacancy_detail) {
    private lateinit var vacancyId: String
    private val viewModelFactory = BaseComponentHolder.get().getViewModelFactory()
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[VacancyDetailViewModel::class.java]
    }
    private lateinit var binding: FragmentVacancyDetailBinding
    private val questionAdapter = QuestionAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVacancyDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadVacancy(vacancyId)
        val buttonBack = view.findViewById<ImageView>(R.id.buttonback_view)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.stateScreenFlow.collect {
                    when (it) {
                        is VacancyDetailViewModel.StateScreen.Content -> {
                            binding.progressbar.isVisible = false
                            binding.buttonRepeatLoad.isVisible = false
                            observeToContent(it.content)
                        }

                        VacancyDetailViewModel.StateScreen.Error -> {
                            binding.progressbar.isVisible = false
                            binding.buttonRepeatLoad.isVisible = true
                        }

                        VacancyDetailViewModel.StateScreen.Progress -> {
                            binding.progressbar.isVisible = true
                            binding.buttonRepeatLoad.isVisible = false
                        }
                    }
                }
            }
        }

        buttonBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().getString(KEY_ID_VACANCY)?.let {
            vacancyId = it
        }
        Log.d("VacancyDetailFragment", "Полученный id $vacancyId")
    }

    private fun observeToContent(vacancy: VacancyDetailUi) {
        binding.titleView.text = vacancy.title
        binding.salaryFullView.text = vacancy.salary
        binding.previewTextView.text =
            "Требуемый опыт: " + vacancy.previewText?.replace("Опыт ", "")
        binding.schedulesView.text =
            vacancy.schedules.substring(0, 1).toUpperCase() + vacancy.schedules.substring(1)
        binding.lookingNumberView.text = resources.getQuantityString(
            R.plurals.lookingNumber,
            vacancy.lookingNumber,
            vacancy.lookingNumber
        ) + "\nсейчас смотрят"
        binding.appliedNumberView.text = resources.getQuantityString(
            R.plurals.lookingNumber,
            vacancy.appliedNumber,
            vacancy.appliedNumber
        ) + " уже откликнулись"
        binding.company.text = vacancy.company
        binding.addressView.text = vacancy.town
        binding.descriptionView.text = vacancy.description
        binding.responsibilitiesView.text = vacancy.responsibilities
        if (vacancy.isFavorite) {
            binding.buttonFavorite.setImageResource(R.drawable.ic_heart_full)
        }else {
            binding.buttonFavorite.setImageResource(R.drawable.ic_heart)
        }
        val recyclerView = binding.rvQuestionsView
        recyclerView.layoutManager =LinearLayoutManager(requireContext())
        recyclerView.adapter = questionAdapter
        questionAdapter.submitList(vacancy.questions)
        binding.buttonFavorite.setOnClickListener {
            viewModel.updateFavoriteStatus(vacancy)
        }
    }

    companion object {
        const val KEY_ID_VACANCY = "id_vacancy"
    }
}