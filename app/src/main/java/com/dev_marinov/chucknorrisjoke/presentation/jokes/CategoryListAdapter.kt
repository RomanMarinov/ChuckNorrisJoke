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
import com.dev_marinov.chucknorrisjoke.presentation.model.SelectableCategory


class CategoryListAdapter(
    private val onItemClick: (position: Int, category: SelectableCategory, width: Int) -> Unit
) : ListAdapter<SelectableCategory, CategoryListAdapter.CategoryViewHolder>(CategoryDiffUtilCallback()) {

    private var categories: List<SelectableCategory> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(listItemBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    override fun submitList(list: MutableList<SelectableCategory>?) {
        super.submitList(list)
        list?.let { this.categories = it.toList() }
    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        private val onItemClick: (position: Int, clickCategory: SelectableCategory, widthTextViewCategory: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: SelectableCategory) {
            binding.category = category

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

            // Метод executePendingBindings используется, чтобы биндинг не откладывался,
            // а выполнился как можно быстрее. Это критично в случае с RecyclerView.
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

    class CategoryDiffUtilCallback : DiffUtil.ItemCallback<SelectableCategory>() {
        override fun areItemsTheSame(
            oldItem: SelectableCategory,
            newItem: SelectableCategory
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SelectableCategory,
            newItem: SelectableCategory
        ): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }
    }
}