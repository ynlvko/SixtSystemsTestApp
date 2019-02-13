package com.sixtsystems.testapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.sixtsystems.testapp.items.edit_item.EditItemFragment
import com.sixtsystems.testapp.items.item_list.ItemListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            setItemListFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showEditItemScreen(itemId: Int?) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .replace(R.id.fragmentContainer, EditItemFragment.newInstance(itemId))
            .addToBackStack(null)
            .commit()
    }

    private fun setItemListFragment() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, ItemListFragment.newInstance())
            .commit()
    }
}
