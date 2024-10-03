package ru.vais.feature.sharit.detail.ui.vacancydetailfeatute.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.vais.feature.sharit.detail.ui.R

class QuestionAdapter: ListAdapter<String, QuestionAdapter.QuestionViewHolder>(QuestionItemDiffCallback()) {

    class QuestionViewHolder(itemView: View):ViewHolder(itemView){
        val questionView = itemView.findViewById<TextView>(R.id.question_view)

        fun bind(str: String){
            questionView.text = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = getItem(position)
        holder.bind(question)
    }
}