package ru.vais.findwork

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.vais.feature.vacancy.data.database.DbUtils

class DbUtilsTest {
    @Test
    fun testConvertQuestionListToStringForListSizeZero() {
        val listItem = mutableListOf<String>()

        val result = DbUtils.convertQuestionListToString(listItem)

        assertEquals("", result)
    }

    @Test
    fun testConvertQuestionListToStringForList() {
        val listItem = mutableListOf<String>()
        listItem.add("Question1")
        listItem.add("Question2")
        listItem.add("Question3")

        val result =  DbUtils.convertQuestionListToString(listItem)

        assertEquals("Question1#Question2#Question3", result)
    }
}
