package com.dev_marinov.chucknorrisjoke.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.dev_marinov.chucknorrisjoke.R
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var arrayList: ArrayList<String> // массив для хранения категорий шуток

    var animationView: LottieAnimationView? = null // анимация на старте

    var mySavedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mySavedInstanceState = savedInstanceState

        animationView = findViewById<LottieAnimationView>(R.id.animationView);
        arrayList = ArrayList()

        supportActionBar?.hide() // скрыть экшен бар
        setWindow() // установки окна

        val runnable1 = Runnable{ // анимация шарики при старте
            animationView?.playAnimation()
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable1, 0)
        animationView?.cancelAnimation()

        val runnable2 = Runnable{ // задержка 1,5 сек перед переходом во FragmentList
            if(mySavedInstanceState == null) {

            val fragmentList = FragmentList()
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.llFragList, fragmentList)
            fragmentTransaction.commit()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable2, 1500)
    }

    fun setWindow() {
        val window = window
        // FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS Флаг, указывающий, что это Окно отвечает за отрисовку фона для системных полос.
        // Если установлено, системные панели отображаются с прозрачным фоном, а соответствующие области в этом окне заполняются
        // цветами, указанными в Window#getStatusBarColor()и Window#getNavigationBarColor().
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent); // прозрачный статус бар
        window.navigationBarColor  = ContextCompat.getColor(this, android.R.color.black); // черный бар навигации
    }


    companion object { // статический интерфейс
        lateinit var myInterFaceCategoryPosWidthTextView: MyInterFaceCategoryPosWidthTextView
        lateinit var myInterFaceWidthTextView : MyInterFaceWidthTextView
    }

    interface MyInterFaceCategoryPosWidthTextView { // интерфейс для передачи данных из адаптера в FragmentList
        fun methodMyInterFaceCategoryPosWidthTextView(myCategory: String?, viewModelSelectPos: Int, viewModelWidthTextView: Int)
    }
    fun setMyInterFaceCategoryPosWidthTextView(myInterFaceCategoryPosWidthTextView: MyInterFaceCategoryPosWidthTextView) {
        Companion.myInterFaceCategoryPosWidthTextView = myInterFaceCategoryPosWidthTextView
    }

    interface MyInterFaceWidthTextView { // интерфейс для передачи данных из адаптера в FragmentList
        fun methodMyInterFaceWidthTextView()
    }
    fun setMyInterFaceWidthTextViewn(myInterFaceWidthTextView: MyInterFaceWidthTextView) {
        Companion.myInterFaceWidthTextView = myInterFaceWidthTextView
    }



}