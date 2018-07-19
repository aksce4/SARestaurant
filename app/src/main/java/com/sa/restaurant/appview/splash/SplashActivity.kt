package com.sa.restaurant.appview.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sa.restaurant.R
import kotlinx.android.synthetic.main.activity_splash.view.*

class SplashActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
    }

}