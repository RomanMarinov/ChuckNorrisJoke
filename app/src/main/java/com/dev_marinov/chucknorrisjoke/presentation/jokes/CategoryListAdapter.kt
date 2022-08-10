package com.dev_marinov.chucknorrisjoke.presentation.jokes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.chucknorrisjoke.R
import com.dev_marinov.chucknorrisjoke.databinding.ItemCategoryBinding
import com.dev_marinov.chucknorrisjoke.domain.Category
import com.dev_marinov.chucknorrisjoke.presentation.model.SelectableItem
import kotlin.collections.ArrayList


class CategoryListAdapter(
    private val onItemClick: (position: Int, category: SelectableItem<Category>, width: Int) -> Unit
) : ListAdapter<SelectableItem<Category>, CategoryListAdapter.CategoryViewHolder>(CategoryDiffUtilCallback()) {

    private var categories: List<SelectableItem<Category>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(listItemBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    override fun submitList(list: MutableList<SelectableItem<Category>>?) {
        super.submitList(list)
        list?.let { this.categories = it.toList() }
    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        private val onItemClick: (position: Int, clickCategory: SelectableItem<Category>, widthTextViewCategory: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: SelectableItem<Category>) {
            binding.category = category

//            binding.tvCategory.text = category.item.name

            val textColorResId = getTextColorResId(category.isSelected)
            val backGroundResId = getBackgroundResId(category.isSelected)

            binding.tvCategory.setTextColor(textColorResId)
            binding.cardView.setBackgroundResource(backGroundResId)
            binding.cardView.setOnClickListener {
                onItemClick(
                    bindingAdapterPosition,
                    category,
                    binding.tvCategory.width
                )
            }

            binding.executePendingBindings()
        }

        private fun getTextColorResId(isSelected: Boolean): Int {
            return if (isSelected)
                ContextCompat.getColor(binding.root.context, R.color.orange)
            else Color.GRAY
        }

        private fun getBackgroundResId(isSelected: Boolean): Int {
            return if (isSelected)
                R.drawable.button_turn_off
            else Color.TRANSPARENT
        }
    }

    class CategoryDiffUtilCallback : DiffUtil.ItemCallback<SelectableItem<Category>>() {
        override fun areItemsTheSame(
            oldItem: SelectableItem<Category>,
            newItem: SelectableItem<Category>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SelectableItem<Category>,
            newItem: SelectableItem<Category>
        ): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }
    }
}