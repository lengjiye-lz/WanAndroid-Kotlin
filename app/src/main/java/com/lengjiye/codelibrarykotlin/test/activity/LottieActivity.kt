package com.lengjiye.codelibrarykotlin.test.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.lengjiye.codelibrarykotlin.R

/**
 * Lottie动画 demo
 */
class LottieActivity : AppCompatActivity() {

    private lateinit var animation_view: LottieAnimationView
    private lateinit var animation_view1: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)
        animation_view = findViewById(R.id.animation_view)


        animation_view.addAnimatorUpdateListener() {
        }

        animation_view.playAnimation()




        animation_view1 = findViewById(R.id.animation_view1)

        animation_view1.addAnimatorUpdateListener() {
            times(it.animatedFraction)
        }

        animation_view1.playAnimation()
    }

    private fun times(i: Float) {
        Log.e("lz", "动画执行进度:${i * 100}")
    }

}



