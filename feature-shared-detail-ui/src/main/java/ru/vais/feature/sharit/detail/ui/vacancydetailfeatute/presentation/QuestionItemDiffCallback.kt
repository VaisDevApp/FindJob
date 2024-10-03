package ru.vais.feature.sharit.detail.ui.vacancydetailfeatute.presentation

import androidx.recyclerview.widget.DiffUtil

class QuestionItemDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}