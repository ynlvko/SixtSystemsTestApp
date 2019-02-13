package com.sixtsystems.testapp.items.item_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sixtsystems.testapp.Navigator
import com.sixtsystems.testapp.R
import com.sixtsystems.testapp.ext.supportActionBar
import com.sixtsystems.testapp.items.Item
import kotlinx.android.synthetic.main.fragmet_item_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemListFragment : Fragment() {
    private lateinit var navigator: Navigator
    private val viewModel: ItemListViewModel by viewModel()

    private val itemsAdapter = ItemsAdapter(itemClickListener = ::onItemClick)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        navigator = activity as Navigator
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmet_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        fabAddNewItem.setOnClickListener(::addNewItem)
        viewModel.viewState().observe(this, Observer { handleViewState(it) })
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setTitle(R.string.app_name)
    }

    private fun initRecyclerView() {
        rvItems.adapter = itemsAdapter
        rvItems.layoutManager = LinearLayoutManager(context)
        rvItems.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        ItemTouchHelper(SwipeToDeleteHelper(::onItemDelete)).attachToRecyclerView(rvItems)
    }

    private fun handleViewState(viewState: ViewState) {
        if (viewState.items.isEmpty()) {
            tvEmptyView.visibility = View.VISIBLE
        } else {
            tvEmptyView.visibility = View.GONE
        }
        itemsAdapter.updateItems(viewState.items.toMutableList())
    }

    private fun addNewItem(v: View) {
        navigator.showEditItemScreen()
    }

    private fun onItemClick(item: Item) {
        navigator.showEditItemScreen(item.id)
    }

    private fun onUndoDelete(v: View) {
        viewModel.undoDelete()
    }

    private fun onItemDelete(position: Int) {
        val deletedItem = itemsAdapter.deleteItem(position) ?: return
        viewModel.removeItem(deletedItem)

        Snackbar.make(
            coordinatorLayout,
            getString(R.string.label_item_deleted, "${deletedItem.firstName} ${deletedItem.lastName}"),
            Snackbar.LENGTH_SHORT
        )
            .setAction(getString(R.string.action_undo), ::onUndoDelete)
            .show()
    }

    companion object {
        fun newInstance() = ItemListFragment()
    }
}
