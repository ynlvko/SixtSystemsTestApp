package com.sixtsystems.testapp.items.item_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sixtsystems.testapp.R
import com.sixtsystems.testapp.items.Item

class ItemsAdapter(
    private var itemList: MutableList<Item> = mutableListOf(),
    private var itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ItemVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        return ItemVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = itemList[position]
        holder.itemView.setOnClickListener {
            itemClickListener.invoke(item)
        }
        holder.bind(item, position)
    }

    fun updateItems(items: MutableList<Item>) {
        itemList = items
        notifyDataSetChanged()
    }

    fun deleteItem(adapterPosition: Int): Item? {
        if (itemList.size < adapterPosition) {
            return null
        }

        val removeItem = itemList.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
        return removeItem
    }
}

class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
    private val tvPosition: TextView = itemView.findViewById(R.id.tvPosition)
    private val tvText: TextView = itemView.findViewById(R.id.tvText)

    fun bind(item: Item, position: Int) {
        ivIcon.setBackgroundResource(item.avatar)
        tvPosition.text = "$position:"
        tvText.text = "${item.firstName} ${item.lastName}"
    }
}

typealias ItemClickListener = (Item) -> Unit

