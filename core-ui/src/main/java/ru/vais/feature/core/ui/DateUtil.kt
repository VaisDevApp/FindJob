package ru.vais.feature.core.ui

import java.text.SimpleDateFormat

object DateUtil {
    private val dateFormatOutput = SimpleDateFormat("dd MMMM")
    private val dateFormatInput = SimpleDateFormat("yyyy-MM-dd")
    fun convert(inputString: String): String {
        val formatter = dateFormatInput.parse(inputString)
        return dateFormatOutput.format(formatter)
    }
}