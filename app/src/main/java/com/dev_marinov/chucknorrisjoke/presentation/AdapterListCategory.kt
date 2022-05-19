package com.dev_marinov.chucknorrisjoke.presentation


import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.chucknorrisjoke.R


class AdapterListCategory(var viewModelSelectPosition: ViewModelSelectPosition,
    var viewModelWidthTextViewCategory: ViewModelWidthTextViewCategory
    ) : RecyclerView.Adapter<AdapterListCategory.ViewHolder>(){

    var arrayList: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rv_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCategory.text = arrayList[position]

        holder.cardView.setOnClickListener(object : View.OnClickListener{ // клик по элементу
            override fun onClick(view: View?) {

                    // запись позиции и обновление
                // Нижняя строка похожа на проверку безопасности, потому что иногда держатель может быть нулевым,
                // в этом случае getAdapterPosition() вернет RecyclerView.NO_POSITION
                if (holder.adapterPosition == RecyclerView.NO_POSITION) return

                //Обновление старых и новых позиций
                notifyItemChanged(viewModelSelectPosition.selectPosition)
                viewModelSelectPosition.selectPosition = holder.adapterPosition
                notifyItemChanged(viewModelSelectPosition.selectPosition)

                Log.e("333","=holder.tvCategory.width="+holder.tvCategory.width)

                viewModelWidthTextViewCategory.widthTextViewCategory = holder.tvCategory.width
                // передача интерфейсу выбранной категории
                MainActivity.myInterFaceCategoryPosWidthTextView.methodMyInterFaceCategoryPosWidthTextView(
                    holder.tvCategory.text.toString(),
                    viewModelSelectPosition.selectPosition,
                    viewModelWidthTextViewCategory.widthTextViewCategory
                ) // передается строка с категорией например "sport"

                Log.e("333","=viewModelSelectPosition.selectPosition="+viewModelSelectPosition.selectPosition)
            }
        })

        // Если selected_position равен или не
        holder.tvCategory.setTextColor(if (viewModelSelectPosition.selectPosition
            == position) Color.parseColor("#FFBB33") else Color.GRAY)

        holder.cardView.setBackgroundResource(if (viewModelSelectPosition.selectPosition
            == position) R.drawable.button_turn_off else Color.TRANSPARENT)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    //передаем данные и оповещаем адаптер о необходимости обновления списка
    fun refreshUsers(arrayList: ArrayList<String>) {
        this.arrayList = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

}