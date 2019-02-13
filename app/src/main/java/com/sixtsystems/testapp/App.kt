package com.sixtsystems.testapp

import android.app.Application
import com.sixtsystems.testapp.items.AvatarsProvider
import com.sixtsystems.testapp.items.DefaultAvatarsProvider
import com.sixtsystems.testapp.items.DefaultItemsRepository
import com.sixtsystems.testapp.items.ItemsRepository
import com.sixtsystems.testapp.items.edit_item.EditItemViewModel
import com.sixtsystems.testapp.items.item_list.ItemListViewModel
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val itemsModule = module {
            single<ItemsRepository> { DefaultItemsRepository() }
            single<AvatarsProvider> { DefaultAvatarsProvider() }
            viewModel { ItemListViewModel(get()) }
            viewModel { (itemId: Int?) -> EditItemViewModel(itemId, get(), get()) }
        }

        startKoin(this, listOf(itemsModule))
    }
}
