package com.dev_marinov.chucknorrisjoke

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.LottieAnimationView
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var arrayList: ArrayList<String> // массив для хранения категорий шуток
    var selected_position: Int? = 6 // при первой загрзке это выбранный номер категории по умолчанию
    var animationView: LottieAnimationView? = null // анимация на старте

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            val fragmentList = FragmentList()
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.llFragList, fragmentList)
            fragmentTransaction.commit()
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


    interface MyInterFace { // интерфейс для передачи данных из адаптера в FragmentList
        fun methodMyInterFace(myCategory: String?)
    }
    fun setMyInterFace(myInterFace: MyInterFace) {
        Companion.myInterFace = myInterFace
    }
    companion object { // статический интерфейс
        lateinit var myInterFace: MyInterFace
    }


}