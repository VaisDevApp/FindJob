package ru.vais.feature.sharit.detail.ui.vacancydetailfeatute.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.vais.feature.sharit.detail.ui.R


class VacancyDetailFragment : Fragment(R.layout.fragment_vacancy_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonBack = view.findViewById<ImageView>(R.id.buttonback_view)
        buttonBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }
}