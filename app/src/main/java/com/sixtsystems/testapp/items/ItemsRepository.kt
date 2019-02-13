package com.sixtsystems.testapp.items

import java.util.*

interface ItemsRepository {
    fun getAllItems(): List<Item>
    fun getItemById(itemId: Int): Item?
    fun addItem(firstName: String, lastName: String, avatar: Int)
    fun updateItem(item: Item)
    fun removeItem(item: Item)
}

class DefaultItemsRepository : ItemsRepository {
    private val itemList = arrayListOf<Item>()
    private var lastItemId = 0

    override fun getAllItems(): List<Item> {
        return itemList
    }

    override fun getItemById(itemId: Int): Item? {
        return itemList.find { it.id == itemId }
    }

    override fun addItem(firstName: String, lastName: String, avatar: Int) {
        val itemToInsert = Item(lastItemId++, firstName, lastName, avatar)
        val positionToInsert = binarySearch(itemList, itemToInsert)
        itemList.add(positionToInsert, itemToInsert)
    }

    override fun updateItem(item: Item) {
        val index = itemList.indexOfFirst { it.id == item.id }
        if (index == -1) {
            return
        }
        val itemAtIndex = itemList[index]
        if (itemAtIndex.firstName != item.firstName || itemAtIndex.lastName != item.lastName) {
            itemList.removeAt(index)
            val newIndex = binarySearch(itemList, item)
            itemList.add(newIndex, item)
        } else {
            itemList[index] = item
        }
    }

    override fun removeItem(item: Item) {
        itemList.remove(item)
    }

    companion object {
        internal fun binarySearch(a: ArrayList<Item>, key: Item): Int {
            var low = 0
            var high = a.size - 1

            while (low <= high) {
                val mid = (low + high) / 2
                val midVal = a[mid]
                val cmp = midVal.compareTo(key)

                when {
                    cmp < 0 -> low = mid + 1
                    cmp > 0 -> high = mid - 1
                    else -> return mid
                } // key found
            }
            return low
        }
    }
}
