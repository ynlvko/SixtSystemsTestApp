package com.sixtsystems.testapp.items.edit_item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sixtsystems.testapp.items.AvatarsProvider
import com.sixtsystems.testapp.items.Item
import com.sixtsystems.testapp.items.ItemsRepository

class EditItemViewModel(
    private val itemId: Int? = null,
    private val itemsRepo: ItemsRepository,
    avatarsProvider: AvatarsProvider
) : ViewModel() {

    private val viewState = MutableLiveData<ViewState>()

    init {
        viewState.value = if (itemId == null) {
            ViewState(
                "", "",
                avatarsProvider.getAvatars(), 0
            )
        } else {
            val item = itemsRepo.getItemById(itemId)
            if (item == null) {
                throw IllegalArgumentException()
            } else {
                ViewState(
                    firstName = item.firstName, lastName = item.lastName,
                    itemAvatars = avatarsProvider.getAvatars(),
                    selectedAvatar = item.avatar
                )
            }
        }
    }

    fun viewState(): LiveData<ViewState> = viewState

    fun save(firstName: String, lastName: String, selectedAvatar: Int) {
        if (itemId == null) {
            itemsRepo.addItem(firstName, lastName, selectedAvatar)
        } else {
            itemsRepo.updateItem(Item(itemId, firstName, lastName, selectedAvatar))
        }
    }
}

data class ViewState(
    val firstName: String,
    val lastName: String,
    val itemAvatars: List<Int>,
    val selectedAvatar: Int
)
