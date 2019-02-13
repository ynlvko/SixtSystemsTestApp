package com.sixtsystems.testapp.items

data class Item(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatar: Int
) : Comparable<Item> {

    override fun compareTo(other: Item): Int {
        val nameComparision = this.firstName.compareTo(other.firstName)
        return when {
            nameComparision < 0 -> -1
            nameComparision > 0 -> 1
            else -> this.lastName.compareTo(other.lastName)
        }
    }
}
