package com.dev_marinov.chucknorrisjoke.presentation

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.chucknorrisjoke.R
import com.dev_marinov.chucknorrisjoke.databinding.RvListBinding

class AdapterListCategory(
    var viewModelSelectPosition: ViewModelSelectPosition,
    var viewModelWidthTextViewCategory: ViewModelWidthTextViewCategory
) : RecyclerView.Adapter<AdapterListCategory.ViewHolder>(){

    var arrayList: ArrayList<String> = ArrayList() // массив для хранения категорий

    lateinit var mListener: onItemClickListener // слушатель кликов для нажатия на кн категорий

    interface onItemClickListener {
        fun onItemClick(position: Int, clickCategory: String, widthTextViewCategory: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = RvListBinding.inflate(inflater, parent, false)
        return ViewHolder(listItemBinding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // передаем в bind значения массива и позицию
        holder.bind(arrayList[position], position)
    }

    override fun getItemCount() = arrayList.size

    //передаем данные и оповещаем адаптер о необходимости обновления списка
    fun refreshUsers(arrayList: ArrayList<String>) {
        Log.e("333","=adapter arrayList=" + arrayList.size)
        this.arrayList = arrayList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RvListBinding, listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, position: Int){
            binding.listItem = item // заполняем tv_category данными

            // выделеный цветом tv_category будет зависеть от наатия
            binding.tvCategory.setTextColor(if (viewModelSelectPosition.selectPosition
                == position) Color.parseColor("#FFBB33") else Color.GRAY)
            binding.cardView.setBackgroundResource(if (viewModelSelectPosition.selectPosition
                == position) R.drawable.button_turn_off else Color.TRANSPARENT)

            // Метод executePendingBindings используется, чтобы биндинг не откладывался,
            // а выполнился как можно быстрее. Это критично в случае с RecyclerView.
            binding.executePendingBindings() // обязательно
        }

        init {
            // пишем клик на элемент и фиксируем данных для своих viewModels
            binding.cardView.setOnClickListener {

                viewModelSelectPosition.selectPosition = bindingAdapterPosition
                notifyDataSetChanged()

                // получаем ширину выбранного по клику view
                viewModelWidthTextViewCategory.widthTextViewCategory = binding.tvCategory.width

                // передача интерфейсу выбранной категории
                listener.onItemClick(
                    viewModelSelectPosition.selectPosition,
                    binding.tvCategory.text.toString(),
                    viewModelWidthTextViewCategory.widthTextViewCategory
                )
            }
        }
    }
}

