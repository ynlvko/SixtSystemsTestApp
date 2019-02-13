package com.sixtsystems.testapp

import com.sixtsystems.testapp.items.Item
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ItemComparisionTest(val data: TestData) {
    @Test
    fun test() {
        assertEquals(data.expectedResult, data.first.compareTo(data.second))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = arrayListOf(
            TestData(-1, item("Aa", "Aa"), item("Ba", "Aa")),
            TestData(-1, item("Aa", "Aa"), item("Aa", "Ba")),
            TestData(0, item("Aa", "Aa"), item("Aa", "Aa")),
            TestData(1, item("Ba", "Aa"), item("Aa", "Aa")),
            TestData(1, item("Aa", "Ba"), item("Aa", "Aa"))
        )

        fun item(firstName: String, lastName: String): Item {
            return Item(0, firstName, lastName, 0)
        }
    }

    class TestData(
        val expectedResult: Int,
        val first: Item,
        val second: Item
    )
}
