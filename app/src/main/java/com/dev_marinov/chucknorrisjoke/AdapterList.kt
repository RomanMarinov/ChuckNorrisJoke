package com.dev_marinov.chucknorrisjoke

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.books.R.color.orange

class AdapterList(val context: Context, var arrayList: ArrayList<String> = ArrayList())
    : RecyclerView.Adapter<AdapterList.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterList.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rv_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterList.ViewHolder, position: Int) {
        holder.tvCategory.setText(arrayList[position])

        holder.cardView.setOnClickListener(object : View.OnClickListener{ // клик по элементу
            override fun onClick(view: View?) {

                // передача интерфейсу выбранной категории
                MainActivity.myInterFace.methodMyInterFace(holder.tvCategory.text.toString())

                    // запись позиции и обновление
                // Нижняя строка похожа на проверку безопасности, потому что иногда держатель может быть нулевым,
                // в этом случае getAdapterPosition() вернет RecyclerView.NO_POSITION
                if (holder.adapterPosition == RecyclerView.NO_POSITION) return

                // Updating old as well as new positions
                notifyItemChanged((context as MainActivity).selected_position!!)
                (context as MainActivity).selected_position = holder.adapterPosition
                notifyItemChanged((context as MainActivity).selected_position!!)
            }
        })

        // Если selected_position равен или не
        holder.tvCategory.setTextColor(if ((context as MainActivity).selected_position!!
            == position) Color.parseColor("#FFBB33") else Color.GRAY)

        holder.cardView.setBackgroundResource(if ((context as MainActivity).selected_position!!
            == position) R.drawable.button_turn_off else Color.TRANSPARENT)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

}