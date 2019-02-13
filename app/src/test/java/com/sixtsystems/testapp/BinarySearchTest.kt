package com.sixtsystems.testapp

import com.sixtsystems.testapp.items.DefaultItemsRepository
import com.sixtsystems.testapp.items.Item
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BinarySearchTest(val data: TestData) {
    @Test
    fun test() {
        assertEquals(data.expectedResult, DefaultItemsRepository.binarySearch(data.itemList, data.item))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = arrayListOf(
            TestData(
                expectedResult = 0,
                itemList = arrayListOf(),
                item = item("Aa", "Aa")
            ),
            TestData(
                expectedResult = 0,
                itemList = arrayListOf(
                    item("Ba", "Aa")
                ),
                item = item("Aa", "Aa")
            ),
            TestData(
                expectedResult = 1,
                itemList = arrayListOf(
                    item("Aa", "Aa")
                ),
                item = item("Ba", "Aa")
            ),
            TestData(
                expectedResult = 1,
                itemList = arrayListOf(
                    item("Aa", "Aa"),
                    item("Ba", "Aa")
                ),
                item = item("Aa", "Ba")
            ),
            TestData(
                expectedResult = 2,
                itemList = arrayListOf(
                    item("Aa", "Aa"),
                    item("Ba", "Aa")
                ),
                item = item("Ca", "Aa")
            )
        )

        fun item(firstName: String, lastName: String): Item {
            return Item(0, firstName, lastName, 0)
        }
    }

    class TestData(
        val expectedResult: Int,
        val itemList: ArrayList<Item>,
        val item: Item
    )

}
