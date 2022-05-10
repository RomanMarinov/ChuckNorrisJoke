package com.dev_marinov.chucknorrisjoke

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class FragmentList : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapterList: AdapterList // адапетр для категорий шуток
    lateinit var tvJoke: TextView // шутка
    var linearLayoutManager: LinearLayoutManager? = null

    var myViewGroup: ViewGroup? = null // контейнер для вьюшек
    var myLayoutInflater: LayoutInflater? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myViewGroup = container
        myLayoutInflater = inflater

        return initInterface()
    }

    fun initInterface() : View {

        val view: View
        // если уже есть надутый макет, удалить его.
        if (myViewGroup != null) {
            myViewGroup!!.removeAllViewsInLayout() // отличается от removeAllView
        }

        // получить экран ориентации
        val orientation = requireActivity().resources.configuration.orientation
        // раздуть соответствующий макет в зависимости от ориентации экрана
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            view = layoutInflater.inflate(R.layout.fragment_list, myViewGroup, false)

            myRecyclerLayoutManagerAdapter(view,  (activity as MainActivity?)?.selected_position)
        } else {
            view = layoutInflater.inflate(R.layout.fragment_list_horizontal, myViewGroup, false)

            myRecyclerLayoutManagerAdapter(view,  (activity as MainActivity?)?.selected_position)
        }
            // если массив с категориями пуст, то сделаем запрос по сети
        if ((activity as MainActivity?)?.arrayList?.size == 0) {
            Log.e("333", "arrayList.size()=" + (activity as MainActivity?)?.arrayList?.size)

            val requestCategoryJoke: RequestCategoryJoke = RequestCategoryJoke()
            requestCategoryJoke.getCategory(context as MainActivity?)

                // интерфейс который сработает после получения данных от сети и обновит адаптер
            requestCategoryJoke.setMyInterFaceAdapterUpdate(object : RequestCategoryJoke.MyInterFaceAdapterUpdate{
                override fun methodMyInterFaceAdapterUpdate() {
                    adapterList.notifyDataSetChanged()
                }
            })

        } else {
            Log.e("333", "FragmentHome arrayList.size()  НЕ ПУСТОЙ=")
        }

        return view
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.e("333", "-зашел FragmentHome onConfigurationChanged-")
        // ДО СОЗДАНИЯ НОВОГО МАКЕТА ПИШЕМ ПЕРЕМЕННЫЕ В КОТОРЫЕ СОХРАНЯЕМ ЛЮБЫЕ ДАННЫЕ ИЗ ТЕКУЩИХ VIEW
        // создать новый макет------------------------------
        val view: View = initInterface()
        // ПОСЛЕ СОЗДАНИЯ НОВОГО МАКЕТА ПЕРЕДАЕМ СОХРАНЕННЫЕ ДАННЫЕ В СТАРЫЕ(ТЕ КОТОРЫЕ ТЕКУЩИЕ) VIEW
        // отображать новую раскладку на экране
        myViewGroup?.addView(view)
        super.onConfigurationChanged(newConfig)
    }

    // метод для установки recyclerview, GridLayoutManager и AdapterListHome
    fun myRecyclerLayoutManagerAdapter(view: View, selected_position: Int?) {
        recyclerView = view.findViewById(R.id.recyclerView)
        tvJoke = view.findViewById(R.id.tvJoke)

        // linearLayoutManager - шахматный порядок
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setLayoutManager(linearLayoutManager)

        adapterList = AdapterList(this.requireActivity(), (activity as MainActivity?)!!.arrayList)
        recyclerView.adapter = adapterList

        val runnable = Runnable { // установка последнего элемента в главном потоке, задержка 0,7сек
            try {
                requireActivity().runOnUiThread {
                    Log.e("333", "-scrollToPositionWithOffset=" + selected_position)
                    recyclerView.smoothScrollToPosition(selected_position!! + 1)

                    cycle(selected_position)
                }
            } catch (e: Exception) {
                Log.e("333", "-try catch FragmentHome 1-$e")
            }
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 700)

            // интерфейс который срабатывает в адаптере по клику на эелемент выбора (т.е. категории шуток)
        (context as MainActivity).setMyInterFace(object : MainActivity.MyInterFace {
            override fun methodMyInterFace(myCategory: String?) {

                // после делаем сетевой запрос с полученной стрингой
                val requestJoke: RequestJoke = RequestJoke()
                requestJoke.getJoke(myCategory!!)
            }
        })

            // и после получения ответа на запрос шутки по категори сработает этот интерфейс,
        // который установит текст шутки
        val requestJoke: RequestJoke = RequestJoke()
        requestJoke.setMyInterFaceSetJoke(object : RequestJoke.MyInterFaceSetJoke{
            override fun methodMyInterFaceSetJoke(value: String) {
                tvJoke.text = value
            }
        })
    }

    fun cycle(selected_position: Int) { // цикл для получения категории по position клик из адаптера
        for (item in (context as MainActivity).arrayList.indices)
            if (selected_position == item) {

                val requestJoke: RequestJoke = RequestJoke() // передача строки категории для получния шутки этой категории
                requestJoke.getJoke((context as MainActivity).arrayList[selected_position])
            }
    }

}