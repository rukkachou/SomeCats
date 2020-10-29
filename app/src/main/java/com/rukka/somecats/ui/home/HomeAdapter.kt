package com.rukka.somecats.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rukka.somecats.databinding.HomeRvHeaderItemBinding
import com.rukka.somecats.databinding.HomeRvMultipleItemBinding
import com.rukka.somecats.databinding.HomeRvSingleItemBinding
import com.rukka.somecats.network.entities.Cat
import com.rukka.somecats.network.entities.NewCats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEAER = 0
private val ITEM_VIEW_TYPE_SINGLE_IMAGE = 1
private val ITEM_VIEW_TYPE_MULTIPLE_IMAGE = 2

class HomeAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallBack()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_HEAER -> {
                val binding = HomeRvHeaderItemBinding.inflate(layoutInflater, parent, false)
                HeaderViewHolder(binding)
            }
            ITEM_VIEW_TYPE_SINGLE_IMAGE -> {
                val binding = HomeRvSingleItemBinding.inflate(layoutInflater, parent, false)
                SingleImageViewHolder(binding)
            }
            ITEM_VIEW_TYPE_MULTIPLE_IMAGE -> {
                val binding = HomeRvMultipleItemBinding.inflate(layoutInflater, parent, false)
                MultipleImageViewHolder(binding)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = getItem(position) as DataItem.HeaderItem
                holder.bind(item.title)
            }
            is SingleImageViewHolder -> {
                val item = getItem(position) as DataItem.SingleImageItem
                holder.bind(item.cat)
            }
            is MultipleImageViewHolder -> {
                val item = getItem(position) as DataItem.MultipleImageItem
                holder.bind(item.cat)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem((position))) {
            is DataItem.HeaderItem -> ITEM_VIEW_TYPE_HEAER
            is DataItem.SingleImageItem -> ITEM_VIEW_TYPE_SINGLE_IMAGE
            is DataItem.MultipleImageItem -> ITEM_VIEW_TYPE_MULTIPLE_IMAGE
        }
    }

    fun submitCustomizedList(list: List<NewCats>) {
        adapterScope.launch{
            val newList = mutableListOf<DataItem>()
            for (item in list) {
                newList.add(DataItem.HeaderItem(item.title))
                val cats = item.cats
                for (cat in cats) {
                    if (cat.images.size >= 3) {
                        newList.add(DataItem.MultipleImageItem(cat))
                    } else {
                        newList.add(DataItem.SingleImageItem(cat))
                    }
                }
            }
            withContext(Dispatchers.Main) {
                submitList(newList)
            }
        }
    }
}

class HeaderViewHolder(val binding: HomeRvHeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(title: String) {
        binding.homeRvHeaderTitle.text = title
        binding.executePendingBindings()
    }
}
class SingleImageViewHolder(val binding: HomeRvSingleItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cat: Cat) {
        binding.cat = cat
        binding.executePendingBindings()
    }
}
class MultipleImageViewHolder(val binding: HomeRvMultipleItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cat: Cat) {
        binding.cat = cat
        binding.executePendingBindings()
    }
}

class DiffCallBack : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return if (oldItem is DataItem.HeaderItem && newItem is DataItem.HeaderItem) {
            oldItem.title == newItem.title
        } else {
            oldItem.id == newItem.id
        }
    }

}

sealed class DataItem {
    data class HeaderItem(val title: String) : DataItem() {
        override val id = 0L
    }

    data class SingleImageItem(val cat: Cat) : DataItem() {
        override val id = cat.id
    }

    data class MultipleImageItem(val cat: Cat) : DataItem() {
        override val id = cat.id
    }

    abstract val id: Long
}