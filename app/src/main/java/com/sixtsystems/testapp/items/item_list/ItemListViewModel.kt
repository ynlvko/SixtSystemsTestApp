package com.sixtsystems.testapp.items.item_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sixtsystems.testapp.items.Item
import com.sixtsystems.testapp.items.ItemsRepository

class ItemListViewModel(
    private val itemsRepo: ItemsRepository
) : ViewModel() {
    private val viewState = MutableLiveData<ViewState>()
    private var removedItem: Item? = null

    fun viewState(): LiveData<ViewState> = viewState

    fun start() {
        updateViewState()
    }

    fun removeItem(item: Item) {
        removedItem = item
        itemsRepo.removeItem(item)
        updateViewState()
    }

    fun undoDelete() {
        removedItem?.let {
            itemsRepo.addItem(it.firstName, it.lastName, it.avatar)
            updateViewState()
        }
        removedItem = null
    }

    private fun updateViewState() {
        viewState.value = ViewState(itemsRepo.getAllItems())
    }
}

data class ViewState(
    val items: List<Item>
)
