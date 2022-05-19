package com.dev_marinov.chucknorrisjoke.presentation

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.chucknorrisjoke.R
import com.dev_marinov.chucknorrisjoke.model.RequestJoke
import androidx.window.layout.WindowMetricsCalculator


class FragmentList : Fragment() {

    lateinit var viewModelListCategory: ViewModelListCategory
    lateinit var viewModelSelectPosition: ViewModelSelectPosition
    lateinit var viewModelWidthTextViewCategory: ViewModelWidthTextViewCategory

    lateinit var recyclerView: RecyclerView
    lateinit var adapterListCategory: AdapterListCategory // адапетр для категорий шуток
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

            myRecyclerLayoutManagerAdapter(view)
        } else {
            view = layoutInflater.inflate(R.layout.fragment_list_horizontal, myViewGroup, false)

            myRecyclerLayoutManagerAdapter(view)
        }

        return view
    }

    // метод для установки recyclerview, GridLayoutManager и AdapterListHome
    fun myRecyclerLayoutManagerAdapter(view: View) {

        viewModelListCategory = ViewModelProvider(this)[ViewModelListCategory::class.java]
        viewModelSelectPosition = ViewModelProvider(this)[ViewModelSelectPosition::class.java]
        viewModelWidthTextViewCategory = ViewModelProvider(this)[ViewModelWidthTextViewCategory::class.java]

        recyclerView = view.findViewById(R.id.recyclerView)
        tvJoke = view.findViewById(R.id.tvJoke)

        // linearLayoutManager - шахматный порядок
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager

        adapterListCategory = AdapterListCategory(
            viewModelSelectPosition,
            viewModelWidthTextViewCategory,
        )
        recyclerView.adapter = adapterListCategory

        // подписываем адаптер на изменение списка
        viewModelListCategory.getArrayCategory().observe(requireActivity(), Observer {
            it.let { adapterListCategory.refreshUsers(it) } // it - обновленный список
        })

        setMyInterFaceCategory(object :FragmentList.MyInterFaceCategory {
            override fun methodMyInterFaceCategory() {
                adapterListCategory.notifyDataSetChanged()
            }
        })

        // СРАБОТАЕТ ПРИ ПЕРВОЙ ЗАГРУЗКЕ МАКЕТА ДЛЯ УСТАНОВКИ УЖЕ ЗАРАНЕЕ ВЫБРАННОЙ КАТЕГОРИИ ПО ЦЕНТРУ
        val runnable2 = Runnable { // установка последнего элемента в главном потоке, задержка 0,7сек
            try {
                requireActivity().runOnUiThread {
                    cycle(viewModelSelectPosition.selectPosition,
                        viewModelWidthTextViewCategory.widthTextViewCategory)
                }
            } catch (e: Exception) {
                Log.e("333", "-try catch FragmentHome 1-$e")
            }
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable2, 700)

        // интерфейс который срабатывает в адаптере по клику на эелемент выбора (т.е. категории шуток)
        // интерфейс, который передет одну выбранную категорию string (сетевой запрос)
        (context as MainActivity).setMyInterFaceCategoryPosWidthTextView(object : MainActivity.MyInterFaceCategoryPosWidthTextView{
            override fun methodMyInterFaceCategoryPosWidthTextView(myCategory: String?,
                                                            viewModelSelectPos: Int,
                                                            viewModelWidthTextView: Int) {
                // после делаем сетевой запрос с полученной стрингой
                val requestJoke = RequestJoke
                requestJoke.getJoke(myCategory!!)

                // получить офсет ширина экрана (можно тут получить) / 2 мнус длина текстВью / 2
                val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity!!)
                val currentBounds = windowMetrics.bounds // E.g. [0 0 1350 1800]
                val width = currentBounds.width()
                val offset = (width / 2) - (viewModelWidthTextViewCategory.widthTextViewCategory / 2)

                // установка textView по центру (когда пользователь кликает на категорию) и позицию
                // использую viewModelSelectPos и viewModelWidthTextView
                val runnable1 = Runnable {
                    try {
                        requireActivity().runOnUiThread {
                            linearLayoutManager!!.scrollToPositionWithOffset(viewModelSelectPosition.selectPosition,offset)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.e("333", "-try catch FragmentList 1-$e")
                    }
                }
                Handler(Looper.getMainLooper()).postDelayed(runnable1, 500)
            }
        })

        //  и после получения ответа на запрос шутки по категори сработает этот интерфейс, который установит текст шутки
        setMyInterFaceJoke(object : FragmentList.MyInterFaceJoke {
            override fun methodMyInterFaceJoke(jokeString: String) {
                tvJoke.text = jokeString
            }
        })

    }
    // ТОЛЬКО ДЛЯ ПЕРВОЙ ЗАГРУЗКИ МАКЕТА
    // цикл для получения категории по position клик из адаптера
    // кога первый раз запускаем экран, viewModelSelectPosition (по умолчанию 6 позиция)
    fun cycle(selected_position: Int, widthTextViewCategory: Int) {
        for (item in viewModelListCategory.getArrayCategory().toString().indices)
            if (selected_position == item) {

                // после делаем сетевой запрос с полученной стрингой
                val requestJoke = RequestJoke
                requestJoke.getJoke(viewModelListCategory.getArrayCategory().value!![selected_position]) // передача строки категории для получния шутки этой категории

                // получить офсет ширина экрана (можно тут получить) / 2 мнус длина текстВью / 2
                val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity!!)
                val currentBounds = windowMetrics.bounds // E.g. [0 0 1350 1800]
                val width = currentBounds.width()

                // поскольку получить ширину TextViewCategory нельзя до ее появления на экране в холдере
                // я узнал ее ширину заранее (213 установил ее в ViewModelWidthTextViewCategory)
                // (эта позиция по умолчанию, установлена во ViewModelSelectPosition)
                val offset = (width / 2) - (widthTextViewCategory / 2)
                Log.e("333", "-widthTextViewCategory=" + widthTextViewCategory)

                // установка textView по центру  и позицию
                // использую viewModelSelectPos и viewModelWidthTextView
                val runnable1 = Runnable {
                    try {
                        requireActivity().runOnUiThread {
                            linearLayoutManager!!.scrollToPositionWithOffset(viewModelSelectPosition.selectPosition,offset)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.e("333", "-try catch FragmentList 1-$e")
                    }
                }
                Handler(Looper.getMainLooper()).postDelayed(runnable1, 500)
            }
    }


    companion object{
        lateinit var myInterFaceCategory: MyInterFaceCategory
        lateinit var myInterFaceJoke: MyInterFaceJoke
    }
    // интерфейс для работы с FragmentPlayers
    interface MyInterFaceCategory{
        fun methodMyInterFaceCategory()
    }
    fun setMyInterFaceCategory(myInterFaceCategory: MyInterFaceCategory) {
        Companion.myInterFaceCategory = myInterFaceCategory
    }
    // интерфейс для работы с FragmentPlayers
    interface MyInterFaceJoke{
        fun methodMyInterFaceJoke(jokeString: String)
    }
    fun setMyInterFaceJoke(myInterFaceJoke: MyInterFaceJoke) {
        Companion.myInterFaceJoke = myInterFaceJoke
    }

}