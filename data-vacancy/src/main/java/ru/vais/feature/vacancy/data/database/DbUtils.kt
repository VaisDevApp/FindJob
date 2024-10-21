package ru.vais.feature.vacancy.data.database

object DbUtils {
    fun convertQuestionListToString(list: List<String>): String {
        return buildString {
            list.forEachIndexed { index, question ->
                if (index == 0) append(question) else append("#$question")
            }
        }
    }
}